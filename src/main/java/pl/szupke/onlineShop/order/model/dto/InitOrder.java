package pl.szupke.onlineShop.order.model.dto;

import lombok.Builder;
import lombok.Getter;
import pl.szupke.onlineShop.order.model.Payment;
import pl.szupke.onlineShop.order.model.Shipment;

import java.util.List;

@Getter
@Builder
public class InitOrder {
    private List<Shipment> shipment;
    private List<Payment> payment;
}
