package com.raquib.cartapi.services;

import com.raquib.cartapi.dtos.OrderDto;
import com.raquib.cartapi.entities.*;
import com.raquib.cartapi.exceptions.CartNotFoundException;
import com.raquib.cartapi.exceptions.CheckoutFailedException;
import com.raquib.cartapi.exceptions.UserNotFoundException;
import com.raquib.cartapi.mappers.OrderMapper;
import com.raquib.cartapi.repositories.CartRepository;
import com.raquib.cartapi.repositories.OrderRepository;
import com.raquib.cartapi.repositories.UserRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;
    private final CartService cartService;

    public OrderService(OrderRepository orderRepository, CartRepository cartRepository, UserRepository userRepository, OrderMapper orderMapper, CartService cartService) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.orderMapper = orderMapper;
        this.cartService = cartService;
    }


    public OrderDto checkout(UUID cartId){
        Cart cart = cartRepository.findById(cartId).orElseThrow(()->new CheckoutFailedException("Checkout process failed: cart not found"));

        if(cart.getItems().isEmpty()){
            throw new CheckoutFailedException("Checkout process failed: cart empty");
        }
        Order order = new Order();
        order.setTotalPrice(cart.getTotalPrice());
        var auth = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) (auth != null ? auth.getPrincipal() : null);

        User user = userRepository.findById(username).orElseThrow(UserNotFoundException::new);
        order.setUser(user);
         order.setStatus(OrderStatus.CREATED);

        for(var item: cart.getItems()){
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setQuantity(item.getQuantity());
            orderItem.setUnitPrice(item.getProduct().getPrice());
            orderItem.setProduct(item.getProduct());

            order.getItems().add(orderItem);
        }
        orderRepository.save(order);
        cartService.clearCart(cart.getId());

        return orderMapper.toDto(order);
    }
}
