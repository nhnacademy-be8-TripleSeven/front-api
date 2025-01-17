package com.tripleseven.frontapi.dto.order;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class OrderManageDTO {
    Long orderId;
    LocalDate orderDate;
    String orderContent;
    int price;
    int amount;
    OrderStatus orderStatus;
    String ordererName;
    String recipientName;
}
