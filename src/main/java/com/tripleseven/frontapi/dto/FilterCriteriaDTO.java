package com.tripleseven.frontapi.dto;

import com.tripleseven.frontapi.dto.order.OrderStatus;
import com.tripleseven.frontapi.dto.order.OrderStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class FilterCriteriaDTO {
    private LocalDate startDate;

    private LocalDate endDate;

    private OrderStatus orderStatus;

    public FilterCriteriaDTO() {
        this.startDate = LocalDate.now().minusMonths(1);
        this.endDate = LocalDate.now();
        this.orderStatus = OrderStatus.ALL;
    }
}
