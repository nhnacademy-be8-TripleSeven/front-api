package com.tripleseven.frontapi.dto.policy;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class DefaultPointPolicyDTO {
    Long id;
    PointPolicyType type;
    Long pointPolicyId;
    String name;
    int amount;
    BigDecimal rate;
}
