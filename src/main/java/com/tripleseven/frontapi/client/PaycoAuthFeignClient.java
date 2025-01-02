package com.tripleseven.frontapi.client;


import com.tripleseven.frontapi.dto.oauth2.payco.PaycoAuthTokenResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "paycoAuthClient", url = "https://id.payco.com/oauth2.0")
public interface PaycoAuthFeignClient {

    @GetMapping("/token")
    PaycoAuthTokenResponseDTO getAccessToken(
            @RequestParam("grant_type") String grantType,
            @RequestParam("client_id") String clientId,
            @RequestParam("client_secret") String clientSecret,
            @RequestParam("code") String code
    );

}
