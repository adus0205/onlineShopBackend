package pl.szupke.onlineShop.review.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.szupke.onlineShop.review.controller.dto.ReviewDto;
import pl.szupke.onlineShop.common.model.Review;
import pl.szupke.onlineShop.review.service.ReviewService;
@RequiredArgsConstructor
@RestController
public class ReviewController {

    private final ReviewService reviewService;
    @PostMapping("/reviews")
    public Review addReview(@RequestBody @Valid ReviewDto reviewDto){
        return reviewService.addReview(Review.builder()
                        .authorName(cleanContent(reviewDto.authorName()))
                        .content(cleanContent(reviewDto.content()))
                        .productId(reviewDto.productId())
                .build());
    }

    private String cleanContent(String text) {
        return Jsoup.clean(text, Safelist.none());
    }


}
