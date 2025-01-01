package com.tripleseven.frontapi.controller.auth;

import com.tripleseven.frontapi.annotations.secure.SecureKey;
import com.tripleseven.frontapi.dto.member.MemberAccountDto;
import com.tripleseven.frontapi.service.oauth2.AfterPaycoLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequiredArgsConstructor
public class AuthController {

    @SecureKey("secret.keys.payco.client-id")
    private String paycoClientId;

    private final AfterPaycoLoginService afterPaycoLoginService;

    @GetMapping("/join")
    public String join() {

        return "auth/join";
    }

    @GetMapping("/login")
    public String login(ModelAndView modelAndView) {
        modelAndView.addObject("paycoClientId", paycoClientId);
        modelAndView.setViewName("auth/login");
        return "auth/login";
    }

    @GetMapping("/oauth2/authorization/payco")
    public String paycoLoginRedirect() {
        String redirectUri = "https://nhn24.store/payco/callback";
        String encodedRedirectUri;
        try {
            encodedRedirectUri = URLEncoder.encode(redirectUri, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Error encoding redirectUri", e);
        }
        String authorizationUri = "https://id.payco.com/oauth2.0/authorize"
                + "?response_type=code"
                + "&client_id=" + paycoClientId
                + "&serviceProviderCode=FRIENDS"
                + "&redirect_uri=" + encodedRedirectUri
                + "&userLocale=ko_KR";

        return "redirect:" + authorizationUri;
    }

    @GetMapping("/payco/callback")
    public ModelAndView callbackPaycoLogin(@RequestParam String code, ModelAndView modelAndView) {
        MemberAccountDto memberAccountDto = afterPaycoLoginService.savePaycoMemberDetail(code);
        modelAndView.addObject("loginId", memberAccountDto.getLoginId());
        modelAndView.setViewName("auth/payco/callback");

        return modelAndView;
    }

    @GetMapping("/find-account")
    public String findAccount() {

        return "auth/find-account";
    }

    @GetMapping("/account/find/email")
    public String findAccountFromEmailForm() {
        return "auth/find-account-email";
    }

    @GetMapping("/account/find/phone")
    public String findAccountFromPhoneForm() {
        return "auth/find-account-phone";
    }

    @GetMapping("/reset-password")
    public ModelAndView resetPasswordForm(@RequestParam String email, @RequestParam String code, ModelAndView modelAndView) {
        modelAndView.addObject("email", email);
        modelAndView.addObject("code", code);
        modelAndView.setViewName("auth/reset-password");
        return modelAndView;
    }
}