package pl.szupke.onlineShop.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.szupke.onlineShop.common.model.Product;
import pl.szupke.onlineShop.common.model.Review;
import pl.szupke.onlineShop.common.repository.ProductRepository;
import pl.szupke.onlineShop.common.repository.ReviewRepository;
import pl.szupke.onlineShop.product.service.dto.ProductDto;
import pl.szupke.onlineShop.product.service.dto.ReviewDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;

    public Page<Product> getProducts (Pageable pageable){
        return productRepository.findAll(pageable);
    }
    @Transactional(readOnly = true)
    public ProductDto getProductBySlug(String slug) {
        Product product = productRepository.findBySlug(slug).orElseThrow();
        List<Review> reviews = reviewRepository.findAllByProductIdAndModerated(product.getId(), true);
        return mapToProductDto(product, reviews);
    }

    private ProductDto mapToProductDto(Product product, List<Review> reviews) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .categoryId(product.getCategoryId())
                .description(product.getDescription())
                .fullDescription(product.getFullDescription())
                .price(product.getPrice())
                .currency(product.getCurrency())
                .image(product.getImage())
                .slug(product.getSlug())
                .reviews(reviews.stream().map(review -> ReviewDto.builder()
                        .id(review.getId())
                        .productId(review.getProductId())
                        .authorName(review.getAuthorName())
                        .content(review.getContent())
                        .moderated(review.isModerated())
                        .build()
                ).toList())
                .build();
    }
}
