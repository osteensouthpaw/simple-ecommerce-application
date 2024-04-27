package com.omega.simpleecommerceapplication.order;

import com.omega.simpleecommerceapplication.orderItem.OrderItem;
import com.omega.simpleecommerceapplication.orderstatus.OrderStatus;
import com.omega.simpleecommerceapplication.user.AppUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "shop_orders")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShopOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "order_total")
    private Double orderTotal;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser appUser;

    @ManyToOne
    @JoinColumn(name = "order_status_id")
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<OrderItem> orderItems;
}
