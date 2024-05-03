package com.omega.simpleecommerceapplication.product;

import com.omega.simpleecommerceapplication.category.ProductCategory;
import com.omega.simpleecommerceapplication.category.ProductCategoryService;
import com.omega.simpleecommerceapplication.commons.PageResponse;
import com.omega.simpleecommerceapplication.exceptions.NoUpdateDetectedException;
import com.omega.simpleecommerceapplication.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

    public PageResponse<Product> findProductsByCategory(
                                                Integer categoryId,
                                                int size,
                                                int page,
                                                String sortField,
                                                String sortDirection) {
        Sort sortOrder = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(Sort.Direction.ASC, sortField) :
                Sort.by(Sort.Direction.DESC, sortField);
        Pageable pageable = PageRequest.of(size, page, sortOrder);
        ProductCategory category = categoryService.findById(categoryId);
        Page<Product> productsByCategoryId = productRepository.findProductsByCategoryId(category.getCategoryId(), pageable);
        return new PageResponse<>(
                productsByCategoryId.getContent(),
                productsByCategoryId.getNumber(),
                productsByCategoryId.getSize(),
                productsByCategoryId.getTotalPages(),
                productsByCategoryId.getTotalElements(),
                productsByCategoryId.isFirst(),
                productsByCategoryId.isLast(),
                productsByCategoryId.getPageable().getOffset()
        );
    }

    public Product findProductById(Integer productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    public PageResponse<Product> findAllProducts(int page,
                                         int size,
                                         String sortField,
                                         String sortDirection) {
        Sort sortOrder = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(Sort.Direction.ASC, sortField) :
                Sort.by(Sort.Direction.DESC, sortField);
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<Product> products = productRepository.findAll(pageable);
        return getProductPageResponse(products);
    }

    private PageResponse<Product> getProductPageResponse(Page<Product> products) {
        return new PageResponse<>(
                products.getContent(),
                products.getNumber(),
                products.getSize(),
                products.getTotalPages(),
                products.getTotalElements(),
                products.isFirst(),
                products.isLast(),
                products.getPageable().getOffset()
        );
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
