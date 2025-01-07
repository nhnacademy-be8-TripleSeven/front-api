package com.tripleseven.frontapi.dto.policy;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class PointPolicyCreateRequestDTO {
    private String name;

    private int amount;

    private BigDecimal rate;
}
