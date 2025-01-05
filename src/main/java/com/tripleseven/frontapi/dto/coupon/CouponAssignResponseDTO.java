

package com.tripleseven.frontapi.dto.coupon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CouponAssignResponseDTO {
    private Long couponId;        // 쿠폰 ID
    private String statusMessage; // 상태 메시지 (예: 성공/실패 메시지)
}
