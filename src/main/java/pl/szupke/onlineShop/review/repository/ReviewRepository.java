package pl.szupke.onlineShop.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.szupke.onlineShop.review.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
