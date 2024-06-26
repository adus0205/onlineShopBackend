package pl.szupke.onlineShop.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.szupke.onlineShop.common.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findBySlug(String slug);
}
