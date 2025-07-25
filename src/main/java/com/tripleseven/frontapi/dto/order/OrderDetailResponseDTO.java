package com.tripleseven.frontapi.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderDetailResponseDTO {
    private final Long id;

    private final Long bookId;

    private final int quantity;

    private final OrderStatus orderStatus;

    private final int primePrice;

    private final int discountPrice;

    private final Long orderGroupId;
}
