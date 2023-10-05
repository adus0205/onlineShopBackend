package pl.szupke.onlineShop.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.szupke.onlineShop.cart.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
