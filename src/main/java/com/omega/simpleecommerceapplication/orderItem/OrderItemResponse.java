package com.omega.simpleecommerceapplication.orderItem;

import com.omega.simpleecommerceapplication.product.ProductDto;

public record OrderItemResponse(
        Integer orderItemId,
        ProductDto productDto,
        int quantity,
        Double subTotal
) {
}
