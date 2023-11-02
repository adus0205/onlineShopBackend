package pl.szupke.onlineShop.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.szupke.onlineShop.order.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
