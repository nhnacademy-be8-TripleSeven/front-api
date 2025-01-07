package com.tripleseven.frontapi.dto.order;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderInfoDTO {
    Long orderDetailId;
    OrderStatus orderStatus;
    String bookName;
    int amount;
    int discountPrice;
    int primePrice;
}
