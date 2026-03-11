package com.raquib.cartapi.controllers;

import com.raquib.cartapi.dtos.AddToCartRequest;
import com.raquib.cartapi.dtos.CartDto;
import com.raquib.cartapi.dtos.CartItemDto;
import com.raquib.cartapi.dtos.UpdateQuantityRequest;
import com.raquib.cartapi.services.CartService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
        var cartDto = cartService.createCart();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cartDto.getId())
                .toUri();
        return ResponseEntity.created(location).body(cartDto);
    }

    @PostMapping("/check")
    public ResponseEntity<String> check(){
        return ResponseEntity.ok("this is okay");
    }
    @PostMapping("add-to-cart/{cartId}")
    public ResponseEntity<CartItemDto> addToCart(@PathVariable UUID cartId, @Valid @RequestBody AddToCartRequest request){
        var cartItemDto = cartService.addToCart(cartId,request.getProductId());
        return ResponseEntity.ok(cartItemDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartDto> getCart(@PathVariable UUID id){
        var cartDto = cartService.getCart(id);
        return ResponseEntity.ok(cartDto);
    }

    @PutMapping("/{cartId}/items/{productId}")
    public ResponseEntity<CartItemDto> updateQuantity(@PathVariable UUID cartId, @PathVariable UUID productId, @Valid @RequestBody UpdateQuantityRequest request){

        var cartItem = cartService.updateQuantity(cartId,productId,request.getQuantity());

        return ResponseEntity.ok(cartItem);
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<Void> removeProductFromCart(@PathVariable UUID cartId, @PathVariable UUID productId){
        cartService.removeProductFromCart(cartId,productId);
        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<Void> clearCart(@PathVariable UUID cartId){
        cartService.clearCart(cartId);
        return ResponseEntity.noContent().build();
    }


}
