package pl.szupke.onlineShop.category.controller;

import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.szupke.onlineShop.common.model.Category;
import pl.szupke.onlineShop.category.dto.CategoryProductDto;
import pl.szupke.onlineShop.category.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Validated
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<Category> getCategories(){
        return categoryService.getCategories();
    }
    @GetMapping("/{slug}/products")
    public CategoryProductDto getCategoriesWithProducts(@PathVariable
                                              @Pattern(regexp = "[a-zA-Z0-9\\-]+")
                                                  @Length(max = 255)String slug, Pageable pageable){
        return categoryService.getCategoriesWithProduct(slug, pageable);
    }
}
