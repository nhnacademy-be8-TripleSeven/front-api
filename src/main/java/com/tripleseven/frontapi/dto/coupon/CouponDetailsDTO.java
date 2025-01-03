
package com.tripleseven.frontapi.dto.coupon;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class CouponDetailsDTO {
    private Long id;
    private String name;
    private Long memberId;
    private LocalDate issueDate;
    private LocalDate expiryDate;
    private String status;
    private LocalDateTime usedAt;
    private BigDecimal discountRate; // 할인률
    private Long discountAmount;
    private String bookTitle;
    private String categoryName;

    public void setStatus(String status) {
        this.status = status;
    }
}