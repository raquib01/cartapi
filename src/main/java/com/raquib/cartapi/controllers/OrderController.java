package com.raquib.cartapi.controllers;

import com.raquib.cartapi.dtos.CheckoutRequest;
import com.raquib.cartapi.dtos.OrderDto;
import com.raquib.cartapi.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<OrderDto> checkout(@RequestBody @Valid CheckoutRequest request){
        var orderDto = orderService.checkout(request.getCartId());

        return ResponseEntity.ok(orderDto);
    }
}
