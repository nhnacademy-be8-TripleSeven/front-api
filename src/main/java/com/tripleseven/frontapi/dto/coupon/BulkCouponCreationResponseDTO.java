package com.tripleseven.frontapi.dto.coupon;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BulkCouponCreationResponseDTO {
    private final boolean success;
    private final long createdCount;
}
