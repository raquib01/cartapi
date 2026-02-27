package com.raquib.cartapi.mappers;

import com.raquib.cartapi.dtos.ProductDto;
import com.raquib.cartapi.entities.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toDto(Product product);
}
