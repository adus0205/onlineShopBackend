package pl.szupke.onlineShop.order.service.payment.p24;

import lombok.Getter;

@Getter
public class TransactionRegisterResponse {

    private Data data;

    record Data (String token) {}
}
