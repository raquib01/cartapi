package com.raquib.cartapi.exceptions;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(){
        super("Product not found");
    }
}
