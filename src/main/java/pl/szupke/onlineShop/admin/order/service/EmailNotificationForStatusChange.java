package pl.szupke.onlineShop.admin.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.szupke.onlineShop.admin.order.model.AdminOrder;
import pl.szupke.onlineShop.common.model.OrderStatus;
import pl.szupke.onlineShop.common.mail.EmailClientService;

import static pl.szupke.onlineShop.admin.order.service.AdminOrderEmailMessage.createCompletedingEmailMessage;
import static pl.szupke.onlineShop.admin.order.service.AdminOrderEmailMessage.createProcessingEmailMessage;
import static pl.szupke.onlineShop.admin.order.service.AdminOrderEmailMessage.createRefundEmailMessage;

@Service
@RequiredArgsConstructor
class EmailNotificationForStatusChange {

    private final EmailClientService emailClientService;

    void sendEmailNotification(OrderStatus newStatus, AdminOrder adminOrder) {
        //statusy PROCESSING, COMPLETED, REFUND
        if (newStatus == OrderStatus.PROCESSING){
            sendEmail(adminOrder.getEmail(), "Zamówienie " + adminOrder.getId() + " zmieniło status na: " + newStatus.getValue(),
                    createProcessingEmailMessage(adminOrder.getId(), newStatus));
        } else if (newStatus == OrderStatus.COMPLETED) {
            sendEmail(adminOrder.getEmail(), "Zamówienie " + adminOrder.getId() + " zostało zrealizowane. ",
                    createCompletedingEmailMessage(adminOrder.getId(), newStatus));
        } else if (newStatus == OrderStatus.REFUND) {
            sendEmail(adminOrder.getEmail(), "Zamówienie " + adminOrder.getId() + " zostało zwrócone. ",
                    createRefundEmailMessage(adminOrder.getId(), newStatus));
        }
    }

    private void sendEmail(String email, String subject, String message) {
        emailClientService.getInstance().send(email, subject, message);
    }
}
