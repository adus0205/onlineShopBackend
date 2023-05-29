package pl.szupke.onlineShop.admin.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.szupke.onlineShop.admin.product.model.AdminProduct;

@Repository
public interface AdminProductRepository extends JpaRepository<AdminProduct, Long> {
}
