package pl.szupke.onlineShop.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.szupke.onlineShop.order.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
