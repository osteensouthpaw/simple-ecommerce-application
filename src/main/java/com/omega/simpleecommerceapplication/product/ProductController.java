package com.omega.simpleecommerceapplication.product;

import com.omega.simpleecommerceapplication.commons.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findProductById(@PathVariable java.lang.Integer id) {
        Product product = productService.findProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping
    public PageResponse<Product> findAllProducts(@RequestParam(required = false, defaultValue = "0") int page,
                                                 @RequestParam(required = false, defaultValue = "10") int size,
                                                 @RequestParam(required = false, defaultValue = "productId") String sortField,
                                                 @RequestParam(required = false, defaultValue = "ASC") String sortDirection) {
        return productService.findAllProducts(page, size, sortField, sortDirection);
    }


    @GetMapping("/categories/{id}")
    public PageResponse<Product> findProductsByCategoryId(
            @PathVariable Integer id,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "categoryId") String sortField,
            @RequestParam(required = false, defaultValue = "ASC") String sortDirection
    ) {
        return productService.findProductsByCategory(id, page, size, sortField, sortDirection);
    }

    @PostMapping("/new")
    public Product createProduct(@RequestBody NewProductRequest request) {
        return productService.save(request);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Integer id,
                                 @RequestBody ProductUpdateRequest product) {
        return productService.updateProductById(id, product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Integer id) {
        productService.deleteProductById(id);
    }
}
