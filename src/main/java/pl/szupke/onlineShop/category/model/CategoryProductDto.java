package pl.szupke.onlineShop.category.model;

import org.springframework.data.domain.Page;
import pl.szupke.onlineShop.product.controller.dto.ProductListDto;

public record CategoryProductDto(Category category, Page<ProductListDto> products) {
}
