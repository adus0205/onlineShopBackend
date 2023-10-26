package pl.szupke.onlineShop.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.szupke.onlineShop.order.model.OrderRow;

public interface OrderRowRepository extends JpaRepository<OrderRow, Long> {
}
