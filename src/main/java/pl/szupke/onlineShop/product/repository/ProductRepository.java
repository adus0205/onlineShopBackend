package pl.szupke.onlineShop.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.szupke.onlineShop.product.model.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
