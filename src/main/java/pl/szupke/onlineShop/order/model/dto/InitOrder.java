package pl.szupke.onlineShop.order.model.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class InitOrder {
    private List<Shipment> shipment;
}
