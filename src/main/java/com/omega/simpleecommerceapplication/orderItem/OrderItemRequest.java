package com.omega.simpleecommerceapplication.orderItem;

public record OrderItemRequest(
        Integer productId,
        int quantity
) {
}
