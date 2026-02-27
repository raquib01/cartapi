package com.raquib.cartapi.services;

import com.raquib.cartapi.dtos.CartDto;
import com.raquib.cartapi.entities.Cart;
import com.raquib.cartapi.entities.Product;
import com.raquib.cartapi.mappers.CartMapper;
import com.raquib.cartapi.repositories.CartRepository;
import com.raquib.cartapi.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

}
