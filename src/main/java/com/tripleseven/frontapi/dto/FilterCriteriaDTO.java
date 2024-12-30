package com.tripleseven.frontapi.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class FilterCriteriaDTO {
    private LocalDate startDate;

    private LocalDate endDate;

    private String status;

    public FilterCriteriaDTO() {
        this.startDate = LocalDate.now().minusMonths(1);
        this.endDate = LocalDate.now();
        this.status = "all";
    }
}
