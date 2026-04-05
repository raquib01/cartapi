package com.raquib.cartapi.services;

import com.raquib.cartapi.dtos.CheckoutSession;
import com.raquib.cartapi.entities.Order;

public interface PaymentGateway {
    CheckoutSession createCheckoutSession(Order order);
}
