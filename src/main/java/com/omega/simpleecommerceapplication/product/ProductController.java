package com.omega.simpleecommerceapplication.product;

import com.omega.simpleecommerceapplication.commons.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findProductById(@PathVariable @RequestPart Integer id) {
        ProductResponse product = productService.findProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping
    public PageResponse<?> findAllProducts(@RequestParam(required = false, defaultValue = "0") int page,
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
    @PreAuthorize("hasAnyRole('ADMIN, MANAGER')")
    public ProductResponse createProduct(@RequestBody NewProductRequest request) {
        return productService.save(request);
    }


    @PostMapping("/uploadImage/{productId}")
    public ResponseEntity<?> uploadProductImage(@RequestPart("file") MultipartFile file,
                                                @PathVariable Integer productId) {
        productService.upload(productId, file);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN, MANAGER')")
    public Product updateProduct(@PathVariable Integer id,
                                 @RequestBody ProductUpdateRequest product) {
        return productService.updateProductById(id, product);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN, MANAGER')")
    public void deleteProduct(@PathVariable Integer id) {
        productService.deleteProductById(id);
    }
}
