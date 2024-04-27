package com.omega.simpleecommerceapplication.orderstatus;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "order_statuses")
@Data
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_status_id")
    private Integer orderStatusId;

    @Column(name = "name")
    private String orderStatusName;
}
