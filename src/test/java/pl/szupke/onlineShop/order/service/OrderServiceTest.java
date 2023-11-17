package pl.szupke.onlineShop.order.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.szupke.onlineShop.common.mail.EmailClientService;
import pl.szupke.onlineShop.common.mail.FakeEmailService;
import pl.szupke.onlineShop.common.model.Cart;
import pl.szupke.onlineShop.common.model.CartItem;
import pl.szupke.onlineShop.common.model.Product;
import pl.szupke.onlineShop.common.repository.CartItemRepository;
import pl.szupke.onlineShop.common.repository.CartRepository;
import pl.szupke.onlineShop.order.model.OrderStatus;
import pl.szupke.onlineShop.order.model.Payment;
import pl.szupke.onlineShop.order.model.PaymentType;
import pl.szupke.onlineShop.order.model.Shipment;
import pl.szupke.onlineShop.order.model.dto.OrderDto;
import pl.szupke.onlineShop.order.model.dto.OrderSummary;
import pl.szupke.onlineShop.order.repository.OrderRepository;
import pl.szupke.onlineShop.order.repository.OrderRowRepository;
import pl.szupke.onlineShop.order.repository.PaymentRepository;
import pl.szupke.onlineShop.order.repository.ShipmentRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private CartRepository cartRepository;
    @Mock
    private ShipmentRepository shipmentRepository;
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderRowRepository orderRowRepository;
    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private EmailClientService emailSender;

    @InjectMocks
    private OrderService orderService;
    @Test
    void shouldPlaceOrder(){
        //given
        OrderDto orderDto = createOrderDto();
        when(cartRepository.findById(any())).thenReturn(createCart());
        when(shipmentRepository.findById(any())).thenReturn(createShipment());
        when(paymentRepository.findById(any())).thenReturn(createPayment());
        when(orderRepository.save(any())).thenAnswer(invocationOnMock -> invocationOnMock.getArguments()[0]);
        when(emailSender.getInstance()).thenReturn(new FakeEmailService());
        //when
        OrderSummary orderSummary = orderService.placeOrder(orderDto);
        //then
        assertThat(orderSummary).isNotNull();
        assertThat(orderSummary.getStatus()).isEqualTo(OrderStatus.NEW);
        assertThat(orderSummary.getGrossValue()).isEqualTo(new BigDecimal("37.22"));
        assertThat(orderSummary.getPayment().getType()).isEqualTo(PaymentType.BANK_TRANSFER);
        assertThat(orderSummary.getPayment().getName()).isEqualTo("test payment");
        assertThat(orderSummary.getPayment().getId()).isEqualTo(1L);
    }

    private Optional<Payment> createPayment() {
        return Optional.of(Payment.builder()
                        .id(1L)
                        .name("test payment")
                        .type(PaymentType.BANK_TRANSFER)
                        .defaultPayment(true)
                .build());
    }

    private Optional<Shipment> createShipment() {
        return Optional.of(Shipment.builder()
                .id(3L)
                .price(new BigDecimal("15.00"))
                .build());
    }

    private Optional<Cart> createCart() {
        return Optional.of(Cart.builder()
                .id(1L)
                .created(LocalDateTime.now())
                .items(createItems())
                .build());
    }

    private List<CartItem> createItems() {
        return List.of(
                CartItem.builder()
                        .id(1L)
                        .cartId(1L)
                        .quantity(1)
                        .product(Product.builder()
                                .id(1L)
                                .price(new BigDecimal("11.11"))
                                .build())
                        .build(),
                CartItem.builder()
                        .id(2L)
                        .cartId(1L)
                        .quantity(1)
                        .product(Product.builder()
                                .id(2L)
                                .price(new BigDecimal("11.11"))
                                .build())
                        .build()
        );
    }

    private OrderDto createOrderDto() {
        return OrderDto.builder()
                .firstname("firstName")
                .lastname("lastName")
                .street("street")
                .zipcode("zipcode")
                .city("city")
                .email("email")
                .phone("phone")
                .cartId(1L)
                .shipmentId(2L)
                .paymentId(3L)
                .build();
    }


}