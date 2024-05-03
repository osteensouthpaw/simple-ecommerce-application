package com.omega.simpleecommerceapplication.orderItem;

import com.omega.simpleecommerceapplication.exceptions.ProductOutOfStockException;
import com.omega.simpleecommerceapplication.exceptions.ResourceNotFoundException;
import com.omega.simpleecommerceapplication.order.ShopOrder;
import com.omega.simpleecommerceapplication.product.Product;
import com.omega.simpleecommerceapplication.product.ProductDtoMapper;
import com.omega.simpleecommerceapplication.product.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final ProductService productService;
    private final ProductDtoMapper productDtoMapper;

    private List<OrderItem> saveAll(List<OrderItem> orderItems) {
        return orderItemRepository.saveAll(orderItems);
    }

    public OrderItem findById(Integer orderItemId) {
        return orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new ResourceNotFoundException("order item not found"));
    }

    @Transactional
    public List<OrderItem> saveOrderItems(ShopOrder order, List<OrderItemRequest> items) {
        List<OrderItem> orderItems = items.stream()
                .map(item -> {
                    Product product = updateProductQuantity(item.productId(), item.quantity());
                    return OrderItem.builder()
                    .product(product)
                    .quantity(item.quantity())
                    .order(order)
                    .build();
                })
                .toList();

        return saveAll(orderItems).stream().limit(100).toList();
    }


    private Product updateProductQuantity(Integer productId, Integer quantityOrdered) {
        Product product = productService.findProductById(productId);
        int quantityInStock = product.getQuantityInStock();

        if (quantityOrdered > quantityInStock)
            throw new ProductOutOfStockException("Requested quantity exceeds the current stock");

        quantityInStock = quantityInStock - quantityOrdered;
        product.setQuantityInStock(quantityInStock);
        return productService.save(product);
    }
}
