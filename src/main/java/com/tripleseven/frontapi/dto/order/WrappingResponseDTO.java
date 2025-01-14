package com.tripleseven.frontapi.dto.order;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Getter
@Slf4j
public class WrappingResponseDTO {
    private final Long id;

    private final String name;

    private final int price;

    @Builder
    private WrappingResponseDTO(Long id, String name, int price) {
        if (Objects.isNull(id)) {
            log.error("Wrapping id cannot be null");
            throw new IllegalArgumentException("id cannot be null");
        }

        this.id = id;
        this.name = name;
        this.price = price;
    }


}
