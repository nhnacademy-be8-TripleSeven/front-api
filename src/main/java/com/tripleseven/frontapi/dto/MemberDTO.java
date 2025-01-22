package com.tripleseven.frontapi.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class MemberDTO {

    private Long id;
    private String email;
    private String phoneNumber;
    private String name;
    private Date birth;
    private String gender;
    private String memberGrade;
    private String password;
    private String postcode;
    private String address;
    private String detailAddress;
    private Integer points;

    private List<MemberAddressDTO> addresses;

    public MemberDTO(Long id, String email, String phoneNumber, String name, Date birth, String gender, String memberGrade, String s, String s1) {
        this.id = id;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.name = name;
    }
}

