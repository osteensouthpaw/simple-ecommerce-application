package com.omega.simpleecommerceapplication.category;

import com.omega.simpleecommerceapplication.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {
    private final ProductCategoryRepository categoryRepository;


    public ProductCategory findById(Integer categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("category not found"));
    }

    public ProductCategory findByName(String name) {
        return categoryRepository.findByCategoryName(name)
                .orElseThrow(() -> new ResourceNotFoundException("category not found"));
    }

    public List<ProductCategory> findAllCategories() {
        return categoryRepository.findAll();
    }

    public ProductCategory save(ProductCategory productCategory) {
        return categoryRepository.save(productCategory);
    }


    public List<ProductCategory> saveAll(List<ProductCategory> productCategories) {
        return categoryRepository.saveAll(productCategories);
    }

    public ProductCategory update(ProductCategory productCategory) {
        //todo check if the update is valid
        ProductCategory category = findById(productCategory.getCategoryId());
        return save(category);
    }

    public void deleteById(Integer categoryId) {
        ProductCategory category = findById(categoryId);
        categoryRepository.deleteById(category.getCategoryId());
    }
}
