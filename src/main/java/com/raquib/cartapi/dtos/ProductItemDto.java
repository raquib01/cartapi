package com.raquib.cartapi.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class ProductItemDto {
    private UUID id;
    private String name;
    private BigDecimal price;
}
