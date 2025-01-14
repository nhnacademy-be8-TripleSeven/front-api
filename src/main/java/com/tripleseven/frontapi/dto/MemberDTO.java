package com.tripleseven.frontapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class MemberDTO {

    private String id;
    private String email;
    private String phoneNumber;
    private String name;
    private Date birth;
    private String gender;
    private String memberGrade;
}
