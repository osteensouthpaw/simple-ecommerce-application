package com.omega.simpleecommerceapplication.category;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
@RequiredArgsConstructor
public class ProductCategoryController {
    private final ProductCategoryService categoryService;

    @GetMapping
    public List<ProductCategory> getAllCategories() {
        return categoryService.findAllCategories();
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
