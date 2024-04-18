package com.omega.simpleecommerceapplication.product;

import com.omega.simpleecommerceapplication.category.ProductCategory;

public record NewProductRequest(
        String productName,
        Double unitPrice,
        String description,
        int quantityInStock,
        String imageUrl,
        ProductCategory productCategory
) {
}
