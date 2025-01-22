package com.tripleseven.frontapi.dto.order;


import lombok.Getter;

@Getter
public enum OrderStatus {
    ERROR("오류"),
    ORDER_CANCELED("주문취소"),
    PAYMENT_PENDING("결제대기"),
    PAYMENT_COMPLETED("결제완료"),
    SHIPPING("배송중"),
    DELIVERED("배송완료"),
    RETURNED_PENDING("반품대기"),
    RETURNED("반품"),
    ALL("전체");

    private final String korean;

    OrderStatus(String korean) {
        this.korean = korean;
    }

}
