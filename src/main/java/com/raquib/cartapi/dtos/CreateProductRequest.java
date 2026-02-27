package com.raquib.cartapi.dtos;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateProductRequest {
    @NotBlank
    private String name;

    private String description;

    @Positive
    @NotNull
    private BigDecimal price;

    @PositiveOrZero
    @NotNull
    private int stock;
}
