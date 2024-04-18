package com.omega.simpleecommerceapplication.product;

import com.omega.simpleecommerceapplication.category.ProductCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "name",
            nullable = false)
    private String productName;

    @Column(nullable = false)
    private String description;

    @Column(name = "unit_price",
            nullable = false)
    private Double unitPrice;

    @Column(name = "quantity_in_stock",
            nullable = false)
    private int quantityInStock;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ProductCategory productCategory;
}
