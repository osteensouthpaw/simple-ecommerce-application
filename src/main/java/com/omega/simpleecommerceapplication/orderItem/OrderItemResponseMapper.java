package com.omega.simpleecommerceapplication.orderItem;

import com.omega.simpleecommerceapplication.product.ProductDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class OrderItemResponseMapper implements Function<OrderItem, OrderItemResponse> {
    private final ProductDtoMapper productDtoMapper;

    @Override
    public OrderItemResponse apply(OrderItem orderItem) {
        double subTotal = orderItem.getProduct().getUnitPrice() * orderItem.getQuantity();
        return new OrderItemResponse(
                orderItem.getOrderItemId(),
                productDtoMapper.apply(orderItem.getProduct()),
                orderItem.getQuantity(),
                subTotal
        );
    }
}
