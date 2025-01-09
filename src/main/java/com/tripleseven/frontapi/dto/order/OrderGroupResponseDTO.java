package com.tripleseven.frontapi.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@AllArgsConstructor
public class OrderGroupResponseDTO {
    private final Long id;

    private final Long userId;

    private final Long wrappingId;

    private final String orderedName;

    private final LocalDate orderedAt;

    private final String recipientName;

    private final String recipientPhone;

    private final String recipientHomePhone;

    private final int deliveryPrice;

    private final String address;

//    @Builder
//    private OrderGroupResponseDTO(Long id, Long userId, Long wrappingId, String orderedName, LocalDate orderedAt, String recipientName, String recipientPhone, String recipientHomePhone, int deliveryPrice, String address) {
//        if (Objects.isNull(id)) {
//            log.error("OrderGroup id cannot be null");
//            throw new IllegalArgumentException("id cannot be null");
//        }
//        this.id = id;
//        this.userId = userId;
//        this.wrappingId = wrappingId;
//        this.orderedName = orderedName;
//        this.orderedAt = orderedAt;
//        this.recipientName = recipientName;
//        this.recipientPhone = recipientPhone;
//        this.recipientHomePhone = recipientHomePhone;
//        this.deliveryPrice = deliveryPrice;
//        this.address = address;
//    }
}