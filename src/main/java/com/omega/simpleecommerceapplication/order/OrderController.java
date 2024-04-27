package com.omega.simpleecommerceapplication.order;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public List<ShopOrder> findAllOrders() {
        return orderService.findAllOrders();
    }


    @GetMapping("/{id}")
    public ShopOrder findOrderById(@PathVariable Integer id) {
        return orderService.findOrderById(id);
    }


    @PostMapping("/new")
    public OrderResponse saveOrder(@RequestBody NewOrderRequest request) {
        return orderService.createOrder(request);
    }
}
