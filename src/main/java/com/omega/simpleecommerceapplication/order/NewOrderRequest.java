package com.omega.simpleecommerceapplication.order;

import com.omega.simpleecommerceapplication.orderItem.OrderItemRequest;

import java.util.List;

public record NewOrderRequest(
        Integer appUserId,
        List<OrderItemRequest> items
) {
}
