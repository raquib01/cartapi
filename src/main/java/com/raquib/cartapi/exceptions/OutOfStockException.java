package com.raquib.cartapi.exceptions;

public class OutOfStockException extends RuntimeException{
    public OutOfStockException(){
        super("Out of stock");
    }
}
