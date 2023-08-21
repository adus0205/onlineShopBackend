package pl.szupke.onlineShop.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.szupke.onlineShop.common.model.Category;
import pl.szupke.onlineShop.category.dto.CategoryProductDto;
import pl.szupke.onlineShop.category.repository.CategoryRepository;
import pl.szupke.onlineShop.common.dto.ProductListDto;
import pl.szupke.onlineShop.common.model.Product;
import pl.szupke.onlineShop.common.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public List<Category> getCategories(){
        return categoryRepository.findAll();
    }
    @Transactional(readOnly = true)
    public CategoryProductDto getCategoriesWithProduct(String slug, Pageable pageable) {
        Category category = categoryRepository.findBySlug(slug);
        Page<Product> page = productRepository.findByCategoryId(category.getId(),pageable);
        List<ProductListDto> productListDtos = page.getContent().stream()
                .map(product -> ProductListDto.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .currency(product.getCurrency())
                        .image(product.getImage())
                        .slug(product.getSlug())
                        .build())
                .toList();
        return new CategoryProductDto(category, new PageImpl<>(productListDtos, pageable, page.getTotalElements()));
    }
}
