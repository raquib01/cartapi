package com.raquib.cartapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CheckoutSession {
    private String checkoutUrl;
}
