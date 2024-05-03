package com.omega.simpleecommerceapplication.orderstatus;

import com.omega.simpleecommerceapplication.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderStatusService {
    private final OrderStatusRepository orderStatusRepository;

    public OrderStatus findOrderStatusById(Integer orderId) {
        return orderStatusRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("OrderStatus not found"));
    }

    public List<OrderStatus> getAllOrderStatuses() {
        return orderStatusRepository.findAll()
                .stream().limit(500)
                .toList();
    }

    public OrderStatus addOderStatus(OrderStatus orderStatus) {
        return orderStatusRepository.save(orderStatus);
    }

    public void deleteOrderStatus(Integer orderId) {
        OrderStatus status = findOrderStatusById(orderId);
        orderStatusRepository.delete(status);
    }
}
