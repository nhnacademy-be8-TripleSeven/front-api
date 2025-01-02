package com.tripleseven.frontapi.dto.oauth2.payco;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString // 테스트 이후 지우기
public class PaycoAuthTokenResponseDTO {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("access_token_secret")
    private String accessTokenSecret;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("expires_in")
    private int expiresIn;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("state")
    private String state;

}
