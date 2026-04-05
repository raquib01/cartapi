package com.raquib.cartapi.dtos;

import com.raquib.cartapi.entities.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class OrderDto {
    private UUID id;
    private OrderStatus status;
    private BigDecimal totalPrice;
    private List<OrderItemDto> items = new ArrayList<>();
    private String sessionUrl;
}
