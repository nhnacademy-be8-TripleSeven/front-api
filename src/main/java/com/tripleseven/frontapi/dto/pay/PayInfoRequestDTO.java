package com.tripleseven.frontapi.dto.pay;

import com.tripleseven.frontapi.dto.order.AddressInfoDTO;
import com.tripleseven.frontapi.dto.order.OrderBookInfoDTO;
import com.tripleseven.frontapi.dto.order.RecipientInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class PayInfoRequestDTO {

    private List<OrderBookInfoDTO> bookOrderDetails;
    // 받는 사람 정보
    private RecipientInfoDTO recipientInfo;

    private AddressInfoDTO addressInfo;

    private LocalDate deliveryDate;//배송 날짜

    private String ordererName; //주문자 이름

    private String payType;

    private Long wrapperId; //포장지 아이디
    private Long couponId; //쿠폰 아이디

    private long point; //사용하는 포인트
    private long totalAmount; //최종 결제 가격
}