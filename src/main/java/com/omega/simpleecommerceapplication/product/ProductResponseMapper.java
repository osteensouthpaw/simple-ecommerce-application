package com.omega.simpleecommerceapplication.product;

import com.omega.simpleecommerceapplication.file.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class ProductResponseMapper implements Function<Product, ProductResponse> {
    private final FileStorageService storageService;

    @Override
    public ProductResponse apply(Product product) {
        return new ProductResponse(
                product.getProductId(),
                product.getProductName(),
                product.getDescription(),
                product.getQuantityInStock(),
                product.getUnitPrice(),
                storageService.downloadFile(product.getImageUrl()),
                product.getProductCategory().getCategoryName()
        );
    }
}
