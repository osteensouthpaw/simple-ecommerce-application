package com.omega.simpleecommerceapplication.product;

import com.omega.simpleecommerceapplication.category.ProductCategory;
import com.omega.simpleecommerceapplication.category.ProductCategoryService;
import com.omega.simpleecommerceapplication.commons.PageResponse;
import com.omega.simpleecommerceapplication.exceptions.NoUpdateDetectedException;
import com.omega.simpleecommerceapplication.exceptions.ResourceNotFoundException;
import com.omega.simpleecommerceapplication.file.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductCategoryService categoryService;
    private final FileStorageService fileStorageService;
    private final ProductResponseMapper productResponseMapper;

    public ProductResponse save(NewProductRequest request) {
        ProductCategory category = categoryService.findById(request.productCategory().getCategoryId());
        Product product = Product.builder()
                .productName(request.productName())
                .unitPrice(request.unitPrice())
                .description(request.description())
                .quantityInStock(request.quantityInStock())
                .productCategory(category)
                .build();
        product = productRepository.save(product);
        return productResponseMapper.apply(product);
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

    public ProductResponse findProductById(Integer productId) {
        return productRepository.findById(productId)
                .map(productResponseMapper)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    public Product getProductById(Integer productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

    }

    public PageResponse<?> findAllProducts(int page,
                                         int size,
                                         String sortField,
                                         String sortDirection) {
        Sort sortOrder = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(Sort.Direction.ASC, sortField) :
                Sort.by(Sort.Direction.DESC, sortField);
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        Page<Product> products = productRepository
                .findAll(pageable);
        return getProductPageResponse(products);
    }

    public Product updateProductById(Integer productId,
                                     ProductUpdateRequest updateRequest) {
        Product product = getProductById(productId);

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

        if (!changes)
            throw new NoUpdateDetectedException("No changes detected");
        return productRepository.save(product);
    }

    public void deleteProductById(Integer productId) {
        productRepository.findById(productId)
                .ifPresentOrElse(productRepository::delete,
                        () -> {
                    throw new ResourceNotFoundException("Product not found");
                });
    }

    public void upload(Integer productId, MultipartFile file) {
        Product product = getProductById(productId);
        String picture = fileStorageService.savePicture(file, product);
        product.setImageUrl(picture);
        productRepository.save(product);
    }


    private PageResponse<?> getProductPageResponse(Page<Product> products) {
        return new PageResponse<>(
                products.getContent().stream()
                        .map(productResponseMapper)
                        .toList(),
                products.getNumber(),
                products.getSize(),
                products.getTotalPages(),
                products.getTotalElements(),
                products.isFirst(),
                products.isLast(),
                products.getPageable().getOffset()
        );
    }
}
