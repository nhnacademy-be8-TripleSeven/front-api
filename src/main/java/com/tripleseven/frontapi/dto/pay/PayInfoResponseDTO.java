package com.tripleseven.frontapi.dto.pay;

import lombok.Getter;

@Getter
public class PayInfoResponseDTO {
    private Long orderId;
    private Long totalAmount;
}