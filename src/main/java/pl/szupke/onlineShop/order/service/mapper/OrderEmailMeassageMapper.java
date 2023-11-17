package pl.szupke.onlineShop.order.service.mapper;

import pl.szupke.onlineShop.order.model.Order;

import java.time.format.DateTimeFormatter;

public class OrderEmailMeassageMapper {

    public static String createEmailMessage(Order order) {
        return  "Twoje zamówienie o id: " + order.getId() +
                "\nData złożenia: " + order.getPlaceDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                "\nWartość: " + order.getGrossValue() + "PLN" +
                "\n\n" +
                "\nMetoda Płatności: " + order.getPayment().getName() +
                (order.getPayment().getNote() != null ? "\n" + order.getPayment().getNote(): "") +
                "\n\nDziękujemy za złożenie zamówienia.";
    }
}
