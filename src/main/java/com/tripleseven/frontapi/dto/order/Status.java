package com.tripleseven.frontapi.dto.order;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum Status {
    PAYMENT_PENDING("결제대기"),
    PAYMENT_COMPLETED("결제완료"),
    SHIPPING("배송중"),
    DELIVERED("배송완료"),
    RETURNED("반품"),
    ORDER_CANCELED("주문취소"),
    ERROR("오류"),
    ALL("전체");
    private final String korean;

    Status(String korean) {
        this.korean = korean;
    }

    @JsonCreator
    public static Status fromString(String str) {
        for (Status status : Status.values()) {
            if (status.name().equalsIgnoreCase(str)) {
                return status;
            }
        }
        return ERROR;
    }

    @JsonValue
    public String toJson() {
        return this.name().toLowerCase();
    }
}
