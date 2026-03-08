package com.raquib.cartapi.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class UpdateQuantityRequest {
    @NotNull
    @Positive
    private int quantity;
}
