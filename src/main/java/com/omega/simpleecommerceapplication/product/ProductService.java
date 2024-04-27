package com.omega.simpleecommerceapplication.product;

import com.omega.simpleecommerceapplication.category.ProductCategory;
import com.omega.simpleecommerceapplication.category.ProductCategoryService;
import com.omega.simpleecommerceapplication.exceptions.NoUpdateDetectedException;
import com.omega.simpleecommerceapplication.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductCategoryService categoryService;

    public Product save(NewProductRequest request) {
        ProductCategory category = categoryService.findById(request.productCategory().getCategoryId());
        Product product = Product.builder()
                .productName(request.productName())
                .unitPrice(request.unitPrice())
                .description(request.description())
                .quantityInStock(request.quantityInStock())
                .imageUrl(request.imageUrl())
                .productCategory(category)
                .build();
        return productRepository.save(product);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public List<Product> findProductsByCategory(Integer categoryId) {
        ProductCategory category = categoryService.findById(categoryId);
        return productRepository.findProductsByProductCategoryCategoryId(category.getCategoryId());
    }

    public Product findProductById(Integer productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public Product updateProductById(Integer productId,
                                     ProductUpdateRequest updateRequest) {
        Product product = findProductById(productId);

        boolean changes = false;

        //update product name
        if (updateRequest.productName() != null && !updateRequest.productName().equals(product.getProductName())) {
            product.setProductName(updateRequest.productName());
            changes = true;
        }

        //update product description
        if (updateRequest.description() != null && !updateRequest.description().equals(product.getDescription())) {
            product.setDescription(updateRequest.description());
            changes = true;
        }

        //update product price
        if (updateRequest.unitPrice() != null && !updateRequest.unitPrice().equals(product.getUnitPrice())) {
            product.setUnitPrice(updateRequest.unitPrice());
            changes = true;
        }

        //update product image
        if (updateRequest.imageUrl() != null && !updateRequest.imageUrl().equals(product.getImageUrl())) {
            product.setImageUrl(updateRequest.imageUrl());
            changes = true;
        }

        if (!changes)
            throw new NoUpdateDetectedException("No changes detected");
        return productRepository.save(product);
    }

    public void deleteProductById(Integer productId) {
        Product product = findProductById(productId);
        productRepository.delete(product);
    }
}
