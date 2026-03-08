package com.raquib.cartapi.services;

import com.raquib.cartapi.dtos.AddToCartResponse;
import com.raquib.cartapi.dtos.CartDto;
import com.raquib.cartapi.dtos.CartItemDto;
import com.raquib.cartapi.entities.Cart;
import com.raquib.cartapi.entities.CartItem;
import com.raquib.cartapi.entities.Product;
import com.raquib.cartapi.mappers.CartMapper;
import com.raquib.cartapi.repositories.CartRepository;
import com.raquib.cartapi.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;


@Service
public class CartService {
    private final CartRepository cartRepository;
    public final ProductRepository productRepository;
    private final CartMapper cartMapper;
    public CartService(CartRepository cartRepository, ProductRepository productRepository, CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartMapper = cartMapper;
    }


    public CartDto createCart(){
        var cart = cartRepository.save(Cart.create());
        return cartMapper.toDto(cart);
    }

    @Transactional
    public AddToCartResponse addToCart(UUID cartId, UUID productId) {

        var cartOpt = cartRepository.findById(cartId);
        if (cartOpt.isEmpty())
            return AddToCartResponse.failure("cart not found");

        var productOpt = productRepository.findById(productId);
        if (productOpt.isEmpty())
            return AddToCartResponse.failure("product not found");

        var cart = cartOpt.get();
        var product = productOpt.get();

        if (product.getStock() == 0)
            return AddToCartResponse.failure("out of stock");

        var existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst();

        if (existingItem.isPresent()) {

            var item = existingItem.get();

            if (item.getQuantity() + 1 > product.getStock())
                return AddToCartResponse.failure("not enough stock");

            item.setQuantity(item.getQuantity() + 1);
            return AddToCartResponse.success(cartMapper.toDto(item));
        }

        var newItem = new CartItem();
        newItem.setCart(cart);
        newItem.setProduct(product);
        newItem.setQuantity(1);

        cart.getItems().add(newItem);

        cartRepository.save(cart);

        return AddToCartResponse.success(cartMapper.toDto(newItem));
    }


    public Optional<CartDto> getCart(UUID id) {
       return cartRepository.findById(id).map(cartMapper::toDto);
    }

    public CartItemDto updateQuantity(UUID cartId, UUID productId, int qty){
        var cart = cartRepository.findById(cartId).orElse(null);
        if(cart==null) return null;

        var cartItem = cart.getItems().stream().filter(item->item.getProduct().getId().equals(productId))
                .findFirst().orElse(null);

        if(cartItem==null) return null;

        cartItem.setQuantity(qty);
        cartRepository.save(cart);
        return cartMapper.toDto(cartItem);


    }

    public boolean removeProductFromCart(UUID cartId, UUID productId) {
        var cart = cartRepository.findById(cartId).orElse(null);
        if(cart==null) return false;

        var cartItem = cart.getItems().stream().filter(item->item.getProduct().getId().equals(productId))
                .findFirst().orElse(null);

        if(cartItem==null) return false;
        cart.getItems().remove(cartItem);
        cartItem.setCart(null);

        cartRepository.save(cart);
        return true;
    }

    public boolean clearCart(UUID cartId) {
        var cart = cartRepository.findById(cartId).orElse(null);
        if(cart==null) return false;

        cart.getItems().clear();
        cartRepository.save(cart);
        return true;
    }
}
