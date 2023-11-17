package pl.szupke.onlineShop.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.szupke.onlineShop.common.mail.EmailClientService;
import pl.szupke.onlineShop.common.model.Cart;
import pl.szupke.onlineShop.common.repository.CartItemRepository;
import pl.szupke.onlineShop.common.repository.CartRepository;
import pl.szupke.onlineShop.order.model.Order;
import pl.szupke.onlineShop.order.model.Payment;
import pl.szupke.onlineShop.order.model.Shipment;
import pl.szupke.onlineShop.order.model.dto.OrderDto;
import pl.szupke.onlineShop.order.model.dto.OrderSummary;
import pl.szupke.onlineShop.order.repository.OrderRepository;
import pl.szupke.onlineShop.order.repository.OrderRowRepository;
import pl.szupke.onlineShop.order.repository.PaymentRepository;
import pl.szupke.onlineShop.order.repository.ShipmentRepository;

import static pl.szupke.onlineShop.order.service.mapper.OrderEmailMeassageMapper.createEmailMessage;
import static pl.szupke.onlineShop.order.service.mapper.OrderMapper.createNewOrder;
import static pl.szupke.onlineShop.order.service.mapper.OrderMapper.createOrderSummary;
import static pl.szupke.onlineShop.order.service.mapper.OrderMapper.mapToOrderRow;
import static pl.szupke.onlineShop.order.service.mapper.OrderMapper.mapToOrderRowWithQuantity;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRowRepository orderRowRepository;
    private final ShipmentRepository shipmentRepository;
    private final PaymentRepository paymentRepository;
    private final EmailClientService emailClientService;
    
    @Transactional
    public OrderSummary placeOrder(OrderDto orderDto) {
        Cart cart = cartRepository.findById(orderDto.getCartId()).orElseThrow();
        Shipment shipment = shipmentRepository.findById(orderDto.getShipmentId()).orElseThrow();
        Payment payment = paymentRepository.findById(orderDto.getPaymentId()).orElseThrow();
        Order newOrder = orderRepository.save(createNewOrder(orderDto, cart, shipment, payment));
        saveOrderRows(cart, newOrder.getId(), shipment);
        clearOrderCart(orderDto);
        sendConfirmemail(newOrder);
        return createOrderSummary(newOrder, payment);
    }

    private void sendConfirmemail(Order newOrder) {
        emailClientService.getInstance()
                .send(newOrder.getEmail(),
                        "Zamówienie zostało przyjęte",
                        createEmailMessage(newOrder));
    }

    private void clearOrderCart(OrderDto orderDto) {
        cartItemRepository.deleteByCartId(orderDto.getCartId());
        cartRepository.deleteCartById(orderDto.getCartId());
    }

    private void saveOrderRows(Cart cart, Long id, Shipment shipment) {
        saveProductRows(cart, id);
        saveShipmentRows(id, shipment);
    }

    private void saveShipmentRows(Long orderId, Shipment shipment) {
        orderRowRepository.save(mapToOrderRow(orderId, shipment));
    }

    private void saveProductRows(Cart cart, Long id) {
        cart.getItems().stream()
                .map(cartItem -> mapToOrderRowWithQuantity(id, cartItem)
                )
                .peek(orderRowRepository::save)
                .toList();
    }

}
