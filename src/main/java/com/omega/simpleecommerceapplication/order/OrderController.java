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
    public List<OrderResponse> findAllOrders() {
        return orderService.findAllOrders();
    }


    @GetMapping("/{id}")
    public OrderResponse findOrderById(@PathVariable Integer id) {
        return orderService.findById(id);
    }


    @PostMapping("/new")
    public OrderResponse saveOrder(@RequestBody NewOrderRequest request) {
        return orderService.createOrder(request);
    }
}
