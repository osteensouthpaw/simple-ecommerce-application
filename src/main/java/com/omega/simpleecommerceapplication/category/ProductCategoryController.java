package com.omega.simpleecommerceapplication.category;

import com.omega.simpleecommerceapplication.commons.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
@RequiredArgsConstructor
public class ProductCategoryController {
    private final ProductCategoryService categoryService;

    @GetMapping
    public PageResponse<ProductCategory> getAllCategories(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "categoryName") String sortField,
            @RequestParam(required = false, defaultValue = "ASC") String sortDirection
    ) {
        return categoryService.findAllCategories(page, size, sortField, sortDirection);
    }

    @GetMapping("/{id}")
    public ProductCategory getCategoryById(@PathVariable Integer id) {
        return categoryService.findById(id);
    }

    @PostMapping("/new")
    public ProductCategory addCategory(@RequestBody ProductCategory category) {
        return categoryService.save(category);
    }

    @PostMapping
    public List<ProductCategory> addCategories(@RequestBody List<ProductCategory> categories) {
        return categoryService.saveAll(categories);
    }

    @PutMapping
    public ProductCategory updateCategory(ProductCategory category) {
        return categoryService.update(category);
    }

    @DeleteMapping("/{id}")
    public void deleteCategoryById(@PathVariable Integer id) {
        categoryService.deleteById(id);
    }
}
