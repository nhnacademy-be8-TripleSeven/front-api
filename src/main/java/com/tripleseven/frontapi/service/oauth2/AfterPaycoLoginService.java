package com.tripleseven.frontapi.service.oauth2;

import com.tripleseven.frontapi.annotations.secure.SecureKey;
import com.tripleseven.frontapi.client.MemberFeignClient;
import com.tripleseven.frontapi.client.PaycoApiFeignClient;
import com.tripleseven.frontapi.client.PaycoAuthFeignClient;
import com.tripleseven.frontapi.dto.member.MemberAccountDto;
import com.tripleseven.frontapi.dto.oauth2.payco.PaycoAuthTokenResponseDTO;
import com.tripleseven.frontapi.dto.oauth2.payco.PaycoMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AfterPaycoLoginService {

    private static final String PAYCO_GRANT_TYPE = "authorization_code";

    @SecureKey("secret.keys.payco.client-id")
    private String paycoClientId;

    @SecureKey("secret.keys.payco.client-secret")
    private String paycoClientSecret;

    private final MemberFeignClient memberFeignClient;
    private final PaycoAuthFeignClient paycoAuthFeignClient;
    private final PaycoApiFeignClient paycoApiFeignClient;

    public MemberAccountDto savePaycoMemberDetail(String code) {
        PaycoAuthTokenResponseDTO accessToken = paycoAuthFeignClient.getAccessToken(
                PAYCO_GRANT_TYPE, paycoClientId, paycoClientSecret, code
        );

        log.info("accessToken : {}", accessToken);
        PaycoMemberDTO paycoMember = paycoApiFeignClient.getPaycoMember(paycoClientId, accessToken.getAccessToken());
        log.info("paycoMember Id : {}", paycoMember.getData().getMember().getIdNo());
        MemberAccountDto memberAccountDto = memberFeignClient.savePaycoMember(paycoMember.getData().getMember());
        log.info("memberAccountDto Id : {}", memberAccountDto.getLoginId());
        return memberAccountDto;
    }

    public void getJwt() {

    }

}
