
package com.tripleseven.frontapi.dto.coupon;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CouponPolicyResponseDTO {

    private Long id;

    private String name;

    private Long couponMinAmount;

    private Long couponMaxAmount;

    private BigDecimal couponDiscountRate;

    private Long couponDiscountAmount;

    private Integer couponValidTime;

}