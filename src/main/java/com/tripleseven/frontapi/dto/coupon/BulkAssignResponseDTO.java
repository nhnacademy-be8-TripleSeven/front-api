package com.tripleseven.frontapi.dto.coupon;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BulkAssignResponseDTO {
    private boolean success;   // 발급 성공 여부
    private int issuedCount;   // 발급된 쿠폰 개수
}