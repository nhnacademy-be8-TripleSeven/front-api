package com.tripleseven.frontapi.dto.order;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
@Getter
public class OrderManageResponseDTO {
    Long orderId;
    LocalDate orderDate;
    String orderContent;
    int price;
    int amount;
    Status status;
    String ordererName;
    String recipientName;

    @Builder
    private OrderManageResponseDTO(Long orderId, LocalDate orderDate, String orderContent, int price, int amount,
                                   Status status, String ordererName, String recipientName) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderContent = orderContent;
        this.price = price;
        this.amount = amount;
        this.status = status;
        this.ordererName = ordererName;
        this.recipientName = recipientName;
    }

    public static OrderManageResponseDTO fromEntity(OrderManageDTO orderManage) {
        return OrderManageResponseDTO.builder()
                .orderId(orderManage.getOrderId())
                .orderDate(orderManage.getOrderDate())
                .orderContent(orderManage.getOrderContent())
                .price(orderManage.getPrice())
                .amount(orderManage.getAmount())
                .status(orderManage.getStatus())
                .ordererName(orderManage.getOrdererName())
                .recipientName(orderManage.getRecipientName())
                .build();
    }
}
