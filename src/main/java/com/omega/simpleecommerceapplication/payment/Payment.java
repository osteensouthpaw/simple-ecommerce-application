package com.omega.simpleecommerceapplication.payment;


import com.omega.simpleecommerceapplication.order.ShopOrder;
import com.omega.simpleecommerceapplication.user.AppUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payments")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentId;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(nullable = false,
            name = "payment_status")
    private String paymentStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUser appUser;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private ShopOrder order;
}
