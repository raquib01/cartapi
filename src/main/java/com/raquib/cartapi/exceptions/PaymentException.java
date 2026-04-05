package com.raquib.cartapi.exceptions;

public class PaymentException extends RuntimeException {
    public PaymentException() {
        super("Payment Exception Occurred");
    }
}
