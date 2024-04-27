package com.omega.simpleecommerceapplication.order;

import com.omega.simpleecommerceapplication.orderItem.OrderItemResponse;
import com.omega.simpleecommerceapplication.user.UserDto;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Integer orderId,
        LocalDateTime orderDate,
        UserDto user,
        List<OrderItemResponse> orderItems,
        Double totalAmount,
        String orderStatus
        ){
}
