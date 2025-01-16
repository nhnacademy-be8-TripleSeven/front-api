package com.tripleseven.frontapi.dto.policy;

import lombok.Value;

@Value
public class DeliveryPolicyUpdateRequestDTO {
    String name;
    int minPrice;
    int price;
}
