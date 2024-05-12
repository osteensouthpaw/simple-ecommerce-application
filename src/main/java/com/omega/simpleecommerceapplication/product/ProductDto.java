package com.omega.simpleecommerceapplication.product;

public record ProductDto(
        Integer productId,
        String productName,
        Double unitPrice,
        byte[] imageUrl
) {
}
