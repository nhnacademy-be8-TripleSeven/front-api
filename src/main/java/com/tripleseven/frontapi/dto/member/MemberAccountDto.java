package com.tripleseven.frontapi.dto.member;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberAccountDto {

    private String loginId;
    private String password;
}
