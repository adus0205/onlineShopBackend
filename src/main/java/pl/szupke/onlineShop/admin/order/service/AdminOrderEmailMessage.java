package pl.szupke.onlineShop.admin.order.service;

import pl.szupke.onlineShop.common.model.OrderStatus;

public class AdminOrderEmailMessage {
    public static String createProcessingEmailMessage(Long id, OrderStatus newStatus) {
        return "Twoje zamówienie o nr" + id + " jest w trakcie przetwarzania. " +
                "\nStatus Twojego zamówienia został zmieniony na: " + newStatus.getValue() +
                "\nTwoje zamówienie jest w trakcie realizacji przez naszych pracowników"+
                "\nPo skompletowaniu z prędkością światła przekażemy je do wysyłki " +
                "\nPozdrawiamy" +
                "\nOnline Shop";
    }

    public static String createCompletedingEmailMessage(Long id, OrderStatus newStatus) {
        return "Twoje zamówienie o nr" + id + " zostsało zrealizowane. " +
                "\nStatus Twojego zamówienia został zmieniony na: " + newStatus.getValue() +
                "\nDziękujemy za zakupy " +
                "\nPolecamy się na przyszłość " +
                "\nPozdrawiamy" +
                "\nOnline Shop";
    }

    public static String createRefundEmailMessage(Long id, OrderStatus newStatus) {
        return "Twoje zamówienie o nr" + id + " zostsało zwrócone. " +
                "\nStatus Twojego zamówienia został zmieniony na: " + newStatus.getValue() +
                "\nPozdrawiamy" +
                "\nOnline Shop";
    }
}
