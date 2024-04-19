package com.omega.simpleecommerceapplication.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findProductById(@PathVariable Integer id) {
        Product product = productService.findProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping
    public List<Product> findAllProducts() {
        return productService.findAllProducts();
    }


    @GetMapping("/categories/{id}")
    public List<Product> findProductsByCategoryId(@PathVariable Integer id) {
        return productService.findProductsByCategory(id);
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
