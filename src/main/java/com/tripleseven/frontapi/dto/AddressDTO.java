package com.tripleseven.frontapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDTO {
    private Long id;
    private String postcode;
    private String roadAddress;
    private String detailAddress;
    private String alias;
    private Boolean isDefault;
}