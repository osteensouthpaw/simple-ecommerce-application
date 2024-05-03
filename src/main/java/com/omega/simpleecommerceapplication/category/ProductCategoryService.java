package com.omega.simpleecommerceapplication.category;

import com.omega.simpleecommerceapplication.commons.PageResponse;
import com.omega.simpleecommerceapplication.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public PageResponse<ProductCategory> findAllCategories(
            int page,
            int size,
            String sortField,
            String sortDirection
    ) {
        Sort sortOrder = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(Sort.Direction.ASC, sortField) :
                Sort.by(Sort.Direction.DESC, sortField);
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<ProductCategory> productCategories = categoryRepository.findAll(pageable);
        return new PageResponse<>(
                productCategories.getContent(),
                productCategories.getNumber(),
                productCategories.getSize(),
                productCategories.getTotalPages(),
                productCategories.getTotalElements(),
                productCategories.isFirst(),
                productCategories.isLast(),
                productCategories.getPageable().getOffset()
        );
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
