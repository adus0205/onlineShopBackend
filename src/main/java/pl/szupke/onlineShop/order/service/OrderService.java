package pl.szupke.onlineShop.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.szupke.onlineShop.common.model.Cart;
import pl.szupke.onlineShop.common.model.CartItem;
import pl.szupke.onlineShop.common.repository.CartItemRepository;
import pl.szupke.onlineShop.common.repository.CartRepository;
import pl.szupke.onlineShop.order.model.Order;
import pl.szupke.onlineShop.order.model.OrderRow;
import pl.szupke.onlineShop.order.model.OrderStatus;
import pl.szupke.onlineShop.order.model.dto.OrderDto;
import pl.szupke.onlineShop.order.model.dto.OrderSummary;
import pl.szupke.onlineShop.order.repository.OrderRepository;
import pl.szupke.onlineShop.order.repository.OrderRowRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRowRepository orderRowRepository;
    @Transactional
    public OrderSummary placeOrder(OrderDto orderDto) {
        // stworzenie zamówienia z wierszami
        Cart cart = cartRepository.findById(orderDto.getCartId()).orElseThrow();
        Order order = Order.builder()
                .firstname(orderDto.getFirstname())
                .lastname(orderDto.getLastname())
                .street(orderDto.getStreet())
                .zipcode(orderDto.getZipcode())
                .city(orderDto.getCity())
                .email(orderDto.getEmail())
                .phone(orderDto.getPhone())
                .placeDate(LocalDateTime.now())
                .orderStatus(OrderStatus.NEW)
                .grossValue(calculateGrossValue(cart.getItems()))
                .build();
        // potem - zapisać zamówienie
        Order newOrder = orderRepository.save(order);
        // pobrac koszyk
        saveOrderRows(cart, newOrder.getId());
        // nastepnie usunąć juz niepotrzebny koszyk
        cartItemRepository.deleteByCartId(orderDto.getCartId());
        cartRepository.deleteCartById(orderDto.getCartId());
        // i na koniec zwrocic podsumowanie zamówienie
        return OrderSummary.builder()
                .id(newOrder.getId())
                .placeDate(newOrder.getPlaceDate())
                .status(newOrder.getOrderStatus())
                .grossValue(newOrder.getGrossValue())
                .build();
    }

    private BigDecimal calculateGrossValue(List<CartItem> items) {
        return items.stream()
                .map(cartItem -> cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    private void saveOrderRows(Cart cart, Long id) {
        cart.getItems().stream()
                .map(cartItem -> OrderRow.builder()
                        .quantity(cartItem.getQuantity())
                        .productId(cartItem.getProduct().getId())
                        .price(cartItem.getProduct().getPrice())
                        .orderId(id)
                        .build()
                )
                .peek(orderRowRepository::save)
                .toList();
    }
}
