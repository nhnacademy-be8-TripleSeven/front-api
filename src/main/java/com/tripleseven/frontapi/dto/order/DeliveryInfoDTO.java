package com.tripleseven.frontapi.dto.order;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class DeliveryInfoDTO {
    String deliveryInfoName;
    int invoiceNumber;
    LocalDate arrivedAt;
    Long orderId;
    LocalDate orderedAt;
    String OrdererName;
    String recipientName;
    String recipientPhone;
    String recipientHomePhone;
    String address;
    LocalDate shippingAt;
}
