package com.raquib.cartapi.mappers;

import com.raquib.cartapi.dtos.CartDto;
import com.raquib.cartapi.dtos.CartItemDto;
import com.raquib.cartapi.entities.Cart;
import com.raquib.cartapi.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(target = "totalPrice",expression = "java(cart.getTotalPrice())")
    CartDto toDto(Cart cart);

    @Mapping(target = "totalPrice", expression = "java(cartItem.getTotalPrice())")
    @Mapping(target = "productId", source = "product.id" )
    @Mapping(target = "productName", source = "product.name" )
    @Mapping(target = "cartId", source = "cart.id" )
    @Mapping(target = "unitPrice", source = "product.price" )
    CartItemDto toDto(CartItem cartItem);
}
