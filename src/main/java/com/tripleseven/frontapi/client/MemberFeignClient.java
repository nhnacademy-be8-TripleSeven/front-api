package com.tripleseven.frontapi.client;

import com.tripleseven.frontapi.dto.member.MemberAccountDto;
import com.tripleseven.frontapi.dto.oauth2.payco.PaycoMemberDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "member-api")
public interface MemberFeignClient {

    @PostMapping("/members/oauth2/payco")
    MemberAccountDto savePaycoMember(@RequestBody PaycoMemberDTO.Body.PaycoMember paycoMember);
}
