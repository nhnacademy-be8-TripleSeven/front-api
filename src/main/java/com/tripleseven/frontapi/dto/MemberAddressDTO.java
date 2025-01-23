package com.tripleseven.frontapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class MemberAddressDTO {
    private Long id;
    private String alias;
    private String roadAddress;
    private String detailAddress;
    private String postcode;
    private Boolean isDefault;
}