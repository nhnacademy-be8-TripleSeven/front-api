package com.tripleseven.frontapi.service;

import com.tripleseven.frontapi.client.MemberFeignClient;
import com.tripleseven.frontapi.dto.AddressDTO;
import com.tripleseven.frontapi.dto.MemberDTO;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberFeignClient memberFeignClient;


    public MemberDTO getMemberInfo(Long userId) {
        return memberFeignClient.getMemberInfo(userId);
    }

    public MemberDTO getMemberInfoWithDefaultAddress(Long userId) {
        MemberDTO member = memberFeignClient.getMemberInfo(userId);

        if (member.getAddress() == null) {
            member = new MemberDTO(member.getId(), member.getEmail(), member.getPhoneNumber(),
                    member.getName(), member.getBirth(), member.getGender(), member.getMemberGrade(),
                    "주소가 없습니다.", "");
        }
        return member;
    }

    public List<AddressDTO> getAddresses(Long userId) {
        return memberFeignClient.getAddresses(userId);
    }

    public String getMembershipGrade(Long userId) {
        MemberDTO member = memberFeignClient.getMember(userId.toString());
        return member.getMemberGrade();
    }

    public boolean verifyPassword(Long userId, String password) {
        Map<String, String> payload = new HashMap<>();
        payload.put("password", password);
        return memberFeignClient.checkPassword(userId, payload);
    }
}