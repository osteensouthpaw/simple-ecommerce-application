package com.omega.simpleecommerceapplication.order;

import com.omega.simpleecommerceapplication.commons.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public PageResponse<OrderResponse> findAllOrders(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "orderId") String sortField,
            @RequestParam(required = false, defaultValue = "ASC") String sortDirection
    ) {
        return orderService.findAllOrders(page, size, sortField, sortDirection);
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
