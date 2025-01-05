package com.tripleseven.frontapi.dto.order;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class OrderPayInfoDTO {
    int totalPrice;
    String paymentKey;
    String paymentName;
    LocalDate requestedAt;
}
