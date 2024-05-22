package pl.szupke.onlineShop.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.szupke.onlineShop.common.mail.EmailClientService;
import pl.szupke.onlineShop.common.model.Cart;
import pl.szupke.onlineShop.common.model.OrderStatus;
import pl.szupke.onlineShop.common.repository.CartItemRepository;
import pl.szupke.onlineShop.common.repository.CartRepository;
import pl.szupke.onlineShop.order.model.Order;
import pl.szupke.onlineShop.order.model.OrderLog;
import pl.szupke.onlineShop.order.model.Payment;
import pl.szupke.onlineShop.order.model.PaymentType;
import pl.szupke.onlineShop.order.model.Shipment;
import pl.szupke.onlineShop.order.model.dto.NotificationReceiveDto;
import pl.szupke.onlineShop.order.model.dto.OrderDto;
import pl.szupke.onlineShop.order.model.dto.OrderListDto;
import pl.szupke.onlineShop.order.model.dto.OrderSummary;
import pl.szupke.onlineShop.order.repository.OrderLogRepository;
import pl.szupke.onlineShop.order.repository.OrderRepository;
import pl.szupke.onlineShop.order.repository.OrderRowRepository;
import pl.szupke.onlineShop.order.repository.PaymentRepository;
import pl.szupke.onlineShop.order.repository.ShipmentRepository;
import pl.szupke.onlineShop.order.service.payment.p24.PaymentMethodP24;

import java.time.LocalDateTime;
import java.util.List;

import static pl.szupke.onlineShop.order.service.mapper.OrderDtoMapper.mapToOrderListDto;
import static pl.szupke.onlineShop.order.service.mapper.OrderEmailMeassageMapper.createEmailMessage;
import static pl.szupke.onlineShop.order.service.mapper.OrderMapper.createNewOrder;
import static pl.szupke.onlineShop.order.service.mapper.OrderMapper.createOrderSummary;
import static pl.szupke.onlineShop.order.service.mapper.OrderMapper.mapToOrderRow;
import static pl.szupke.onlineShop.order.service.mapper.OrderMapper.mapToOrderRowWithQuantity;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRowRepository orderRowRepository;
    private final ShipmentRepository shipmentRepository;
    private final PaymentRepository paymentRepository;
    private final EmailClientService emailClientService;
    private final PaymentMethodP24 paymentMethodP24;
    private final OrderLogRepository orderLogRepository;
    
    @Transactional
    public OrderSummary placeOrder(OrderDto orderDto, Long userId) {
        Cart cart = cartRepository.findById(orderDto.getCartId()).orElseThrow();
        Shipment shipment = shipmentRepository.findById(orderDto.getShipmentId()).orElseThrow();
        Payment payment = paymentRepository.findById(orderDto.getPaymentId()).orElseThrow();
        Order newOrder = orderRepository.save(createNewOrder(orderDto, cart, shipment, payment, userId));
        saveOrderRows(cart, newOrder.getId(), shipment);
        clearOrderCart(orderDto);
        sendConfirmemail(newOrder);
        String redirectUrl = initPaymentIfNeeded(newOrder);
        return createOrderSummary(newOrder, payment, redirectUrl);
    }

    private String initPaymentIfNeeded(Order newOrder) {
        if (newOrder.getPayment().getType() == PaymentType.P24_ONLINE){
            return paymentMethodP24.initPayment(newOrder);
        }
        return null;
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

    public List<OrderListDto> getOrdersForCustomer(Long userId) {
        return mapToOrderListDto(orderRepository.findByUserId(userId));
    }

    public Order getOrderByOrderHash(String orderHash) {
        return orderRepository.findByOrderHash(orderHash).orElseThrow();
    }

    @Transactional
    public void receiveNotification(String orderHash, NotificationReceiveDto receiveDto, String remoteAddr) {
        Order order = getOrderByOrderHash(orderHash);
        String status = paymentMethodP24.receiveNotification(order, receiveDto, remoteAddr);
        if (status.equals("success")){
            OrderStatus oldStatus = order.getOrderStatus();
            order.setOrderStatus(OrderStatus.PAID);
            orderLogRepository.save(OrderLog.builder()
                            .created(LocalDateTime.now())
                            .orderId(order.getId())
                            .note("Zamówienie zostało opłacone przez serwis Przelewy24, id płatności : " + receiveDto.getStatement() + " , zmieniono status z" + oldStatus.getValue() + " na  " + order.getOrderStatus().getValue())
                    .build());
        }
    }
}
