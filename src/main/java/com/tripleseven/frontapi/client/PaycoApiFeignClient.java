package com.tripleseven.frontapi.client;

import com.tripleseven.frontapi.dto.oauth2.payco.PaycoMemberDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "paycoApiClient", url = "https://apis-payco.krp.toastoven.net")
public interface PaycoApiFeignClient {

    @PostMapping("/payco/friends/find_member_v2.json")
    PaycoMemberDTO getPaycoMember(@RequestHeader("client_id") String clientId, @RequestHeader("access_token") String accessToken);
}
