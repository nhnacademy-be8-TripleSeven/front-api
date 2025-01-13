package com.tripleseven.frontapi.dto.order;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class RecipientInfoDTO {
    private String recipientName;     // 받는 사람 이름
    private String recipientPhone;    // 받는 사람 휴대폰 번호
    private String recipientLandline; // 받는 사람 일반 전화
}
