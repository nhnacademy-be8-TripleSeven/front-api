package com.tripleseven.frontapi.dto.order;

import lombok.Value;

import java.time.LocalDate;

@Value
public class OrderManageRequestDTO {
    LocalDate startDate;
    LocalDate endDate;
    Status status;
}
