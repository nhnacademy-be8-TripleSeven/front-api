package com.tripleseven.frontapi.dto.order;

import lombok.Getter;

@Getter
public class OrderGroupInfoDTO {
    int primeTotalPrice;
    int discountedPrice;
    int deliveryPrice;
    String wrappingName;
    int wrappingPrice;
    int totalPrice;
    int usedPoint;
    int earnedPoint;
}
