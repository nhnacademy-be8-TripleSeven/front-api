package com.tripleseven.frontapi.service;

import com.tripleseven.frontapi.client.MemberFeignClient;
import com.tripleseven.frontapi.dto.AddressDTO;
import com.tripleseven.frontapi.dto.MemberDTO;
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

    public List<AddressDTO> getAddresses(Long userId) {
        return memberFeignClient.getAddresses(userId);
    }
}