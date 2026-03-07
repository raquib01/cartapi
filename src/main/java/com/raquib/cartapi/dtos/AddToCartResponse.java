package com.raquib.cartapi.dtos;

import com.raquib.cartapi.entities.CartItem;
import lombok.Getter;

@Getter
public class AddToCartResponse {
    private CartItemDto cartItem;
    private String errorMsg;


    public AddToCartResponse(CartItemDto cartItem, String errorMsg) {
        this.cartItem = cartItem;
        this.errorMsg = errorMsg;
    }

    public static AddToCartResponse success(CartItemDto cartItem){
        return new AddToCartResponse(cartItem,null);
    }

    public static AddToCartResponse failure(String errorMsg){
        return new AddToCartResponse(null,errorMsg);
    }

    public boolean isSuccess(){
        return this.cartItem != null;
    }


}
