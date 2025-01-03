package com.tripleseven.frontapi.dto.order;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderInfoDTO {
    Status status;
    String bookName;
    int amount;
    int discountPrice;
    int primePrice;
}
