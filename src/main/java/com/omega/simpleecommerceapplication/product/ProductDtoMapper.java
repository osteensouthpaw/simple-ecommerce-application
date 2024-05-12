package com.omega.simpleecommerceapplication.product;

import com.omega.simpleecommerceapplication.file.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class ProductDtoMapper implements Function<Product, ProductDto> {
    private final FileStorageService fileStorageService;
    @Override
    public ProductDto apply(Product product) {
        return new ProductDto(
                product.getProductId(),
                product.getProductName(),
                product.getUnitPrice(),
                fileStorageService.downloadFile(product.getImageUrl())
        );
    }
}
