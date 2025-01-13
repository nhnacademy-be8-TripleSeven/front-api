package com.tripleseven.frontapi.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class AddressInfoDTO {
    private String roadAddress;     // 도로명 주소
    private String zoneAddress;     // 지번 주소
    private String detailAddress;   // 상세 주소
}
