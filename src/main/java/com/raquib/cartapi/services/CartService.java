package com.raquib.cartapi.services;

import com.raquib.cartapi.dtos.CartDto;
import com.raquib.cartapi.dtos.CartItemDto;
import com.raquib.cartapi.entities.Cart;
import com.raquib.cartapi.entities.CartItem;
import com.raquib.cartapi.exceptions.CartNotFoundException;
import com.raquib.cartapi.exceptions.OutOfStockException;
import com.raquib.cartapi.exceptions.ProductNotFoundException;
import com.raquib.cartapi.mappers.CartMapper;
import com.raquib.cartapi.repositories.CartRepository;
import com.raquib.cartapi.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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
    public CartItemDto addToCart(UUID cartId, UUID productId) {

        var cart = cartRepository.findById(cartId).orElseThrow(CartNotFoundException::new);

        var product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);

        if (product.getStock() == 0)
            throw new OutOfStockException();

        var existingItem = cart.getItem(productId);

        if (existingItem != null) {
            if (existingItem.getQuantity() + 1 > product.getStock())
                throw new OutOfStockException();

             existingItem.setQuantity(existingItem.getQuantity() + 1);
            return cartMapper.toDto(existingItem);
        }

        var newItem = new CartItem();
        newItem.setCart(cart);
        newItem.setProduct(product);
        newItem.setQuantity(1);

        cart.getItems().add(newItem);

        cartRepository.save(cart);

        return cartMapper.toDto(newItem);
    }


    public CartDto getCart(UUID id) {
       var cart = cartRepository.findById(id).orElseThrow(CartNotFoundException::new);
       return cartMapper.toDto(cart);
    }

    public CartItemDto updateQuantity(UUID cartId, UUID productId, int qty){
        var cart = cartRepository.findById(cartId).orElseThrow(CartNotFoundException::new);


        var cartItem = cart.getItem(productId);

        if(cartItem==null) throw new ProductNotFoundException();

        cartItem.setQuantity(qty);
        cartRepository.save(cart);
        return cartMapper.toDto(cartItem);


    }

    public void removeProductFromCart(UUID cartId, UUID productId) {
        var cart = cartRepository.findById(cartId).orElseThrow(CartNotFoundException::new);


        var cartItem = cart.getItem(productId);

        if(cartItem==null) throw new ProductNotFoundException();

        cart.getItems().remove(cartItem);
        cartItem.setCart(null);

        cartRepository.save(cart);
    }

    public void clearCart(UUID cartId) {
        var cart = cartRepository.findById(cartId).orElseThrow(CartNotFoundException::new);

        cart.getItems().clear(); // it creates N delete queries, for optimization, custom query can be used
        cartRepository.save(cart);
    }
}
