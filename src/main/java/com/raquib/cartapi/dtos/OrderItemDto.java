package com.raquib.cartapi.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class OrderItemDto {
    private UUID id;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private Integer quantity;
    private ProductItemDto product;
}
