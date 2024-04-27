package com.omega.simpleecommerceapplication.product;

import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ProductDtoMapper implements Function<Product, ProductDto> {
    @Override
    public ProductDto apply(Product product) {
        return new ProductDto(
                product.getProductId(),
                product.getProductName(),
                product.getUnitPrice(),
                product.getImageUrl()
        );
    }
}
