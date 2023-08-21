package pl.szupke.onlineShop.category.dto;

import org.springframework.data.domain.Page;
import pl.szupke.onlineShop.common.model.Category;
import pl.szupke.onlineShop.common.dto.ProductListDto;

public record CategoryProductDto(Category category, Page<ProductListDto> products) {
}
