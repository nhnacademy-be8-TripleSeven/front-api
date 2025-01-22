package com.tripleseven.frontapi.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class OrderCalculationResult {
    private int productAmount;
    private int discountAmount;
    private int finalAmount;
    private int deliveryPrice;
    private int deliveryMinPrice;
    private int additionalAmount;
    private int availablePoint;
}
