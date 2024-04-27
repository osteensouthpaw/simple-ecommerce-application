package com.omega.simpleecommerceapplication.order;

import com.omega.simpleecommerceapplication.exceptions.ResourceNotFoundException;
import com.omega.simpleecommerceapplication.orderItem.OrderItem;
import com.omega.simpleecommerceapplication.orderItem.OrderItemRequest;
import com.omega.simpleecommerceapplication.orderItem.OrderItemService;
import com.omega.simpleecommerceapplication.orderstatus.OrderStatus;
import com.omega.simpleecommerceapplication.orderstatus.OrderStatusService;
import com.omega.simpleecommerceapplication.product.Product;
import com.omega.simpleecommerceapplication.product.ProductService;
import com.omega.simpleecommerceapplication.user.AppUser;
import com.omega.simpleecommerceapplication.user.AppUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final AppUserService userService;
    private final OrderStatusService statusService;
    private final ProductService productService;
    private final OrderItemService orderItemService;
    private final OrderResponseMapper orderResponseMapper;


    public List<ShopOrder> findAllOrders() {
        return orderRepository.findAll();
    }

    public ShopOrder findOrderById(Integer id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("order does not exists"));
    }

    @Transactional
    public OrderResponse createOrder(NewOrderRequest orderRequest) {
        AppUser appUser = userService.findByUserById(orderRequest.appUserId());
        List<OrderItemRequest> items = orderRequest.items();

        //implement some logic to change orderStatus based on the current state of the order
        OrderStatus orderStatus = statusService.findOrderStatusById(1);
        ShopOrder order = ShopOrder.builder()
                .orderDate(LocalDateTime.now())
                .appUser(appUser)
                .orderStatus(orderStatus)
                .orderTotal(calculateOrderTotal(items))
                .build();
        ShopOrder savedOrder = orderRepository.save(order);
        List<OrderItem> orderItems = orderItemService.saveOrderItems(savedOrder, items);
        savedOrder.setOrderItems(orderItems);
        return orderResponseMapper.apply(savedOrder);
    }

    public void deleteOrder(Integer orderId) {
        ShopOrder order = findOrderById(orderId);
        orderRepository.delete(order);
    }

    private double calculateOrderTotal(List<OrderItemRequest> items) {
        double totalPrice = Double.MIN_VALUE;
        for (var item : items) {
            Product product = productService.findProductById(item.productId());
            totalPrice += product.getUnitPrice() * item.quantity();
        }
        return totalPrice;
    }
}
