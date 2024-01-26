package pl.szupke.onlineShop.order.service.mapper;

import pl.szupke.onlineShop.order.model.Order;
import pl.szupke.onlineShop.order.model.dto.OrderListDto;

import java.util.List;

public class OrderDtoMapper {

    public static List<OrderListDto> mapToOrderListDto(List<Order> orders) {
        return orders.stream()
                .map(order -> new OrderListDto(
                        order.getId(),
                        order.getPlaceDate(),
                        order.getOrderStatus().getValue(),
                        order.getGrossValue()

                )).toList();
    }
}
