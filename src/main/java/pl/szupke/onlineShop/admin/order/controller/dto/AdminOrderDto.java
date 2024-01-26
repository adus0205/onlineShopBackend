package pl.szupke.onlineShop.admin.order.controller.dto;

import lombok.Builder;
import lombok.Getter;
import pl.szupke.onlineShop.common.model.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class AdminOrderDto {

    private Long id;
    private LocalDateTime placeDate;
    private OrderStatus orderStatus;
    private BigDecimal grossValue;
}
