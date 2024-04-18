package com.omega.simpleecommerceapplication.product;

public record ProductUpdateRequest(
        String productName,
        Double unitPrice,
        String description,
        int quantityInStock,
        String imageUrl
) {
}
