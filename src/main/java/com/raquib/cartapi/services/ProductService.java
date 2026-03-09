package com.raquib.cartapi.services;

import com.raquib.cartapi.dtos.CreateProductRequest;
import com.raquib.cartapi.dtos.ProductDto;
import com.raquib.cartapi.entities.Product;
import com.raquib.cartapi.mappers.ProductMapper;
import com.raquib.cartapi.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public ProductDto createProduct(CreateProductRequest productObj) {
        var product = productRepository.save(Product.create(productObj.getName(),productObj.getDescription(),productObj.getPrice(),productObj.getStock()));
        return productMapper.toDto(product);
    }
}
