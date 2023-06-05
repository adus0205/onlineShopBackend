package pl.szupke.onlineShop.admin.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.szupke.onlineShop.admin.category.model.AdminCategory;

public interface AdminCategoryRepository extends JpaRepository<AdminCategory, Long> {
}
