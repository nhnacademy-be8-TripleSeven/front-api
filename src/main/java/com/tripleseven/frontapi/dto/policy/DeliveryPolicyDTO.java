package com.tripleseven.frontapi.dto.policy;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DeliveryPolicyDTO {
    Long id;
    String name;
    int price;
}
