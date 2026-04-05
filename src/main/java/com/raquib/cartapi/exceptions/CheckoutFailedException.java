package com.raquib.cartapi.exceptions;

public class CheckoutFailedException extends RuntimeException {
    private Boolean isPaymentFailed = false;

    public void setPaymentFailed(){
        this.isPaymentFailed = true;
    }
    public boolean isPaymentFailed(){
        return this.isPaymentFailed;
    }
    public CheckoutFailedException(String message) {
        super(message);
    }
}
