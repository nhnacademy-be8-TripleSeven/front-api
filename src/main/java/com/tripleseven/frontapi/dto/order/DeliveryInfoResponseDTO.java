package com.tripleseven.frontapi.dto.order;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Slf4j
public class DeliveryInfoResponseDTO {
    private final Long id;

    private final String name;

    private final int invoiceNumber;

    private final LocalDate arrivedAt;

    private final LocalDate shippingAt;

    @Builder
    private DeliveryInfoResponseDTO(Long id, String name, int invoiceNumber, LocalDate arrivedAt, LocalDate shippingAt) {
        if (Objects.isNull(id)) {
            log.error("DeliveryInfo id cannot be null");
            throw new IllegalArgumentException("id cannot be null");
        }
        this.id = id;
        this.name = name;
        this.invoiceNumber = invoiceNumber;
        this.arrivedAt = arrivedAt;
        this.shippingAt = shippingAt;
    }


}
