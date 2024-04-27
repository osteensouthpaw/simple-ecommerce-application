package com.omega.simpleecommerceapplication.order;

import com.omega.simpleecommerceapplication.orderItem.OrderItemResponseMapper;
import com.omega.simpleecommerceapplication.user.UserDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class OrderResponseMapper implements Function<ShopOrder, OrderResponse> {
    private final UserDtoMapper userDtoMapper;
    private final OrderItemResponseMapper orderItemResponseMapper;

    @Override
    public OrderResponse apply(ShopOrder order) {
        return new OrderResponse(
                order.getOrderId(),
                order.getOrderDate(),
                userDtoMapper.apply(order.getAppUser()),
                order.getOrderItems()
                        .stream()
                        .map(orderItemResponseMapper)
                        .toList(),
                order.getOrderTotal(),
                order.getOrderStatus()
                        .getOrderStatusName()
        );
    }
}
