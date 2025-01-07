package com.tripleseven.frontapi.dto.policy;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class PointPolicyUpdateRequestDTO {
    private String name;

    private int amount;

    private BigDecimal rate;
}
