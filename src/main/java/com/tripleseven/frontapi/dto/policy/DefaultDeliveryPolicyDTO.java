package com.tripleseven.frontapi.dto.policy;

import lombok.Getter;

@Getter
public class DefaultDeliveryPolicyDTO {
    Long id;
    String name;
    Integer minPrice;
    Integer price;
    DeliveryPolicyType type;
}
