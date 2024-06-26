package pl.szupke.onlineShop.order.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.szupke.onlineShop.common.model.OrderStatus;
import pl.szupke.onlineShop.order.controller.dto.NotificationDto;
import pl.szupke.onlineShop.order.model.dto.NotificationReceiveDto;
import pl.szupke.onlineShop.order.model.Order;
import pl.szupke.onlineShop.order.model.dto.InitOrder;
import pl.szupke.onlineShop.order.model.dto.OrderDto;
import pl.szupke.onlineShop.order.model.dto.OrderListDto;
import pl.szupke.onlineShop.order.model.dto.OrderSummary;
import pl.szupke.onlineShop.order.service.OrderService;
import pl.szupke.onlineShop.order.service.PaymentService;
import pl.szupke.onlineShop.order.service.ShipmentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
@Validated
public class OrderController {

    private final OrderService orderService;
    private final ShipmentService shipmentService;
    private final PaymentService paymentService;

    @PostMapping()
    public OrderSummary placeOrder(@RequestBody OrderDto orderDto, @AuthenticationPrincipal Long userId){
        return orderService.placeOrder(orderDto, userId);
    }

    @GetMapping("/initData")
    public InitOrder initOrder(){
        return InitOrder.builder()
                .shipment(shipmentService.getShipments())
                .payment(paymentService.getPayments())
                .build();
    }

    @GetMapping
    public List<OrderListDto> getOrders (@AuthenticationPrincipal Long userId){
        if (userId == null){
            throw new IllegalArgumentException("Brak użytkownika");
        }
        return orderService.getOrdersForCustomer(userId);
    }

    @GetMapping("/notification/{orderHash}")
    public NotificationDto notificationShow(@PathVariable @Length(max = 12) String orderHash){
        Order order = orderService.getOrderByOrderHash(orderHash);
        return new NotificationDto(order.getOrderStatus() == OrderStatus.PAID);
    }

    @PostMapping("/notification/{orderHash}")
    public void notificationReceive(@PathVariable @Length(max = 12) String orderHash, @RequestBody NotificationReceiveDto receiveDto, HttpServletRequest request){
        orderService.receiveNotification(orderHash, receiveDto, request.getRemoteAddr());
    }
}
