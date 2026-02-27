package com.raquib.cartapi.dtos;

import lombok.Getter;
import lombok.Setter;


import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class CartDto {
    private UUID id;
    private Set<CartItemDto> items;
    private BigDecimal totalPrice;
}
