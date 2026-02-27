package com.raquib.cartapi.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class CartItemDto {
    private UUID id;
    private Integer quantity;
    private BigDecimal totalPrice;
    private String productId;
    private String productName;
    private String cartId;
    private String unitPrice;
}
