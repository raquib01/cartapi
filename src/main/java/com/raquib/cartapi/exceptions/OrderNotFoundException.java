package com.raquib.cartapi.exceptions;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException() {
        super("order not found");
    }
}
