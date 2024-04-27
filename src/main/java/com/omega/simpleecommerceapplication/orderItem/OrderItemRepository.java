package com.omega.simpleecommerceapplication.orderItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    @Query(
        value = """
        SELECT SUM(p.unit_price * oi.quantity_ordered)
        FROM products p
        JOIN order_items oi ON p.product_id = oi.product_id
        WHERE oi.order_id = :orderId
        """,
            nativeQuery = true
    )
    Double getTotalPrice(@Param("orderId") Integer orderId);

    //find all order-items for a particular order id
    @Query(
            value = """
            SELECT * FROM order_items WHERE order_id = :orderId
            """,
            nativeQuery = true
    )
    List<OrderItem> findOrderItemsByOrderId(@Param("orderId") Integer orderId);
}
