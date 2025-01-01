package com.tripleseven.frontapi.dto;

import com.tripleseven.frontapi.dto.order.Status;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class FilterCriteriaDTO {
    private LocalDate startDate;

    private LocalDate endDate;

    private Status status;

    public FilterCriteriaDTO() {
        this.startDate = LocalDate.now().minusMonths(1);
        this.endDate = LocalDate.now();
        this.status = Status.ALL;
    }
}
