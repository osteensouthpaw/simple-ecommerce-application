package com.omega.simpleecommerceapplication.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, java.lang.Integer> {
    List<Product> findProductsByProductCategoryCategoryId(java.lang.Integer category);

}
