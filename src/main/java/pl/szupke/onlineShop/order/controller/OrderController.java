package pl.szupke.onlineShop.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
