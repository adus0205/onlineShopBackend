package pl.szupke.onlineShop.order.service.mapper;

import pl.szupke.onlineShop.common.model.Cart;
import pl.szupke.onlineShop.common.model.CartItem;
import pl.szupke.onlineShop.order.model.Order;
import pl.szupke.onlineShop.order.model.OrderRow;
import pl.szupke.onlineShop.order.model.OrderStatus;
import pl.szupke.onlineShop.order.model.Payment;
import pl.szupke.onlineShop.order.model.Shipment;
import pl.szupke.onlineShop.order.model.dto.OrderDto;
import pl.szupke.onlineShop.order.model.dto.OrderSummary;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderMapper {

    public static Order createNewOrder(OrderDto orderDto, Cart cart, Shipment shipment, Payment payment) {
        return Order.builder()
                .firstname(orderDto.getFirstname())
                .lastname(orderDto.getLastname())
                .street(orderDto.getStreet())
                .zipcode(orderDto.getZipcode())
                .city(orderDto.getCity())
                .email(orderDto.getEmail())
                .phone(orderDto.getPhone())
                .placeDate(LocalDateTime.now())
                .orderStatus(OrderStatus.NEW)
                .grossValue(calculateGrossValue(cart.getItems(), shipment))
                .payment(payment)
                .build();
    }

    private static BigDecimal calculateGrossValue(List<CartItem> items, Shipment shipment) {
        return items.stream()
                .map(cartItem -> cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO)
                .add(shipment.getPrice());
    }

    public static OrderSummary createOrderSummary(Order newOrder, Payment payment) {
        return OrderSummary.builder()
                .id(newOrder.getId())
                .placeDate(newOrder.getPlaceDate())
                .status(newOrder.getOrderStatus())
                .grossValue(newOrder.getGrossValue())
                .payment(payment)
                .build();
    }

    public static OrderRow mapToOrderRow(Long id, Shipment shipment) {
        return OrderRow.builder()
                .quantity(1)
                .price(shipment.getPrice())
                .shipmentId(shipment.getId())
                .orderId(id)
                .build();
    }

    public static OrderRow mapToOrderRowWithQuantity(Long id, CartItem cartItem) {
        return OrderRow.builder()
                .quantity(cartItem.getQuantity())
                .productId(cartItem.getProduct().getId())
                .price(cartItem.getProduct().getPrice())
                .orderId(id)
                .build();
    }
}
