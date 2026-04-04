package com.raquib.cartapi.controllers;

import com.raquib.cartapi.dtos.CheckoutRequest;
import com.raquib.cartapi.dtos.OrderDto;
import com.raquib.cartapi.entities.Order;
import com.raquib.cartapi.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @GetMapping
    public ResponseEntity<List<OrderDto>> getOrders(){
        var orders = orderService.getOrdersForCurrentUser();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable UUID orderId){
        var order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }
}
