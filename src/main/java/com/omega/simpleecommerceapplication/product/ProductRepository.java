package com.omega.simpleecommerceapplication.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("""
            SELECT product
            FROM Product product
            WHERE product.productCategory.categoryId = :categoryId
"""
    )
    Page<Product> findProductsByCategoryId(Integer categoryId, Pageable pageable);

}
