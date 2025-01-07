package com.tripleseven.frontapi.service;

import com.tripleseven.frontapi.client.MemberFeignClient;
import com.tripleseven.frontapi.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberFeignClient memberFeignClient;

    public MemberDTO getMemberInfo(Long userId){
        return memberFeignClient.getMemberInfo(userId);
    }
}
