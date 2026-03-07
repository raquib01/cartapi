package com.raquib.cartapi.mappers;

import com.raquib.cartapi.dtos.CartDto;
import com.raquib.cartapi.dtos.CartItemDto;
import com.raquib.cartapi.dtos.ProductItemDto;
import com.raquib.cartapi.entities.Cart;
import com.raquib.cartapi.entities.CartItem;
import com.raquib.cartapi.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(target = "totalPrice",expression = "java(cart.getTotalPrice())")
    CartDto toDto(Cart cart);

    @Mapping(target = "totalPrice", expression = "java(cartItem.getTotalPrice())")
    @Mapping(target = "cartId", source = "cart.id" )
    CartItemDto toDto(CartItem cartItem);

    ProductItemDto toDto(Product product);
}
