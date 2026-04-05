package com.raquib.cartapi.services;

import com.raquib.cartapi.dtos.CheckoutSession;
import com.raquib.cartapi.dtos.OrderDto;
import com.raquib.cartapi.entities.*;
import com.raquib.cartapi.exceptions.CheckoutFailedException;
import com.raquib.cartapi.exceptions.OrderNotFoundException;
import com.raquib.cartapi.exceptions.PaymentException;
import com.raquib.cartapi.exceptions.UserNotFoundException;
import com.raquib.cartapi.mappers.OrderMapper;
import com.raquib.cartapi.repositories.CartRepository;
import com.raquib.cartapi.repositories.OrderRepository;
import com.raquib.cartapi.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;
    private final CartService cartService;
    private final PaymentGateway paymentGateway;


    public OrderService(OrderRepository orderRepository, CartRepository cartRepository, UserRepository userRepository, OrderMapper orderMapper, CartService cartService, PaymentGateway paymentGateway) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.orderMapper = orderMapper;
        this.cartService = cartService;
        this.paymentGateway = paymentGateway;
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
         order.setStatus(OrderStatus. PENDING_PAYMENT);

        for(var item: cart.getItems()){
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setQuantity(item.getQuantity());
            orderItem.setUnitPrice(item.getProduct().getPrice());
            orderItem.setProduct(item.getProduct());

            order.getItems().add(orderItem);
        }
        orderRepository.save(order);

        // create payment session
        try {
            var session = paymentGateway.createCheckoutSession(order);
            cartService.clearCart(cart.getId());

            var orderDto = orderMapper.toDto(order);
            orderDto.setSessionUrl(session.getCheckoutUrl());
            return orderDto;
        }catch (PaymentException ex){
            order.setStatus(OrderStatus.FAILED);
            orderRepository.save(order);
            throw ex;
        }
    }

     public List<OrderDto> getOrdersForCurrentUser(){
         var auth = SecurityContextHolder.getContext().getAuthentication();
         String username = (String) (auth != null ? auth.getPrincipal() : null);

         User user = userRepository.findById(username).orElseThrow(UserNotFoundException::new);

          var orders = orderRepository.getOrderByUser(user);

          return orders.stream().map(orderMapper::toDto).toList();

     }

     public OrderDto getOrderById(UUID orderId){

        var order = orderRepository.getOrderById(orderId).orElseThrow(OrderNotFoundException::new);

         var auth = SecurityContextHolder.getContext().getAuthentication();
         String username = (String) (auth != null ? auth.getPrincipal() : null);

         User user = userRepository.findById(username).orElseThrow(UserNotFoundException::new);


         if(!user.getUsername().equals(order.getUser().getUsername())){
             throw new OrderNotFoundException();
         }

         return orderMapper.toDto(order);
     }

     public void updateOrderStatus(String orderId){
        var orderOpt = orderRepository.findById(UUID.fromString(orderId));
        if(orderOpt.isPresent()){
            var order = orderOpt.get();

            if(order.getStatus() == OrderStatus.PAID){
                return; // ✅ already processed
            }
            order.setStatus(OrderStatus.PAID);
            orderRepository.save(order);
        }
     }

}
