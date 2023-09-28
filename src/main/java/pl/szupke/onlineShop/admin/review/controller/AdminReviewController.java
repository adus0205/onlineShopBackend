package pl.szupke.onlineShop.admin.review.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.szupke.onlineShop.admin.review.model.AdminReview;
import pl.szupke.onlineShop.admin.review.service.AdminReviewService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("admin/reviews")
public class AdminReviewController {

    private final AdminReviewService adminReviewService;
    @GetMapping
    public List<AdminReview> getReviews(){
        return adminReviewService.getReviews();
    }

    @PutMapping("/{id}/moderated")
    public void moderate(@PathVariable Long id){
        adminReviewService.moderate(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        adminReviewService.delete(id);
    }

}
