package pl.szupke.onlineShop.order.service.payment.p24;

import lombok.Getter;

@Getter
public class TransactionVerifyResponse {

    private Data data;

    record Data(String status) {}
}
