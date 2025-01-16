package com.tripleseven.frontapi.dto.order;

import com.tripleseven.frontapi.dto.coupon.AvailableCouponResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

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
    private List<AvailableCouponResponseDTO> couponList;
}
