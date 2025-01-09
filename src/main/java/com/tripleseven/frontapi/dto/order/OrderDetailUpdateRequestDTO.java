package com.tripleseven.frontapi.dto.order;

import lombok.Value;

import java.util.List;

@Value
public class OrderDetailUpdateRequestDTO {
    List<Long> orderIds;
    OrderStatus orderStatus;
}
