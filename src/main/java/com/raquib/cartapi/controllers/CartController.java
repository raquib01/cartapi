package com.raquib.cartapi.controllers;

import com.raquib.cartapi.dtos.AddToCartRequest;
import com.raquib.cartapi.dtos.CartDto;
import com.raquib.cartapi.services.CartService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/api/carts")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<CartDto> createCart(){
        var cart = cartService.createCart();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cart.getId())
                .toUri();
        return ResponseEntity.created(location).body(cart);
    }

    @PostMapping("add-to-cart/{cartId}")
    public ResponseEntity<?> addToCart(@PathVariable UUID cartId, @Valid @RequestBody AddToCartRequest request){
        var response = cartService.addToCart(cartId,request.getProductId());

        if(response.isSuccess()) return ResponseEntity.ok(response.getCartItem());

        var errorMsg = response.getErrorMsg();
        if("cart not found".equals(errorMsg)) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMsg);
        if("product not found".equals(errorMsg)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMsg);

        return ResponseEntity.badRequest().body(errorMsg);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCart(@PathVariable UUID id){
        var cartDto = cartService.getCart(id);
        if(cartDto.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("cart not found");
        return ResponseEntity.ok(cartDto.get());
    }


}
