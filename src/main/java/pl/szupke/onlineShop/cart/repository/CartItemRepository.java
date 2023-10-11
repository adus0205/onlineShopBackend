package pl.szupke.onlineShop.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.szupke.onlineShop.cart.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Long countByCartId(Long cartId);
}
