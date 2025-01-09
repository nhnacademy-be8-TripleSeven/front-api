package com.tripleseven.frontapi.dto.policy;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class DefaultPolicyDTO {
    List<DefaultPointPolicyDTO> pointPolicies;
    List<DefaultDeliveryPolicyDTO> deliveryPolicies;
}
