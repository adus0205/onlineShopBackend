package pl.szupke.onlineShop.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.szupke.onlineShop.order.model.Shipment;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
}
