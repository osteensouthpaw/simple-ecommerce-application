package com.omega.simpleecommerceapplication.product;

public record ProductResponse(
        Integer productId,
        String productName,
        String productDescription,
        int quantityInStock,
        Double unitPrice,
        byte[] imageUrl,
        String category
) {
}
