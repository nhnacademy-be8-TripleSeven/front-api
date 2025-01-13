package com.tripleseven.frontapi.dto.pay;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class PayInfoRequestDTO {


    // 받는 사람 정보
    private String recipientName;     // 받는 사람 이름
    private String recipientPhone;    // 받는 사람 휴대폰 번호
    private String recipientLandline; // 받는 사람 일반 전화

    private String roadAddress;     // 도로명 주소
    private String zoneAddress;     // 지번 주소
    private String detailAddress;   // 상세 주소

    private LocalDate deliveryDate;//배송 날짜

    private long wrapperId; //포장지 아이디
    private long couponId; //쿠폰 아이디

    private long point; //사용하는 포인트
    private long totalAmount; //총 가격
}