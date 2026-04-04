package com.raquib.cartapi.mappers;

import com.raquib.cartapi.dtos.OrderDto;
import com.raquib.cartapi.dtos.OrderItemDto;
import com.raquib.cartapi.dtos.ProductDto;
import com.raquib.cartapi.entities.Order;
import com.raquib.cartapi.entities.OrderItem;
import com.raquib.cartapi.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDto toDto(Order order);

    @Mapping(target = "totalPrice", expression = "java(items.getTotalPrice())")
    OrderItemDto toDto(OrderItem items);
    ProductDto toDto(Product product);
}
