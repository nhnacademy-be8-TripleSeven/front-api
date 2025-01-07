package com.tripleseven.frontapi.dto.policy;

import lombok.Value;

@Value
public class DeliveryPolicyCreateRequestDTO {
    String name;
    int price;
}
