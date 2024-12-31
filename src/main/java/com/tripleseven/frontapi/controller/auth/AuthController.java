package com.tripleseven.frontapi.controller.auth;

import com.tripleseven.frontapi.annotations.secure.SecureKey;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
public class AuthController {

    @SecureKey("secret.keys.payco.client-id")
    private String paycoClientId;
    @SecureKey("secret.keys.payco.client-secret")
    private String paycoClientSecret;

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
        String redirectUri = "https://nhn24.store/backend/auth/payco/callback";
        String encodedRedirectUri;
        try {
            encodedRedirectUri = URLEncoder.encode(redirectUri, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Error encoding redirectUri", e);
        }
        String authorizationUri = "https://id.payco.com/oauth2.0/authorize"
                + "?response_type=code"
                + "&client_id=" + paycoClientId
                + "&redirect_uri=" + encodedRedirectUri
                + "&userLocale=ko_KR";

        return "redirect:" + authorizationUri;
    }

    @GetMapping("/account/find")
    public String findAccount() {

        return "/auth/find-account";
    }

    @GetMapping("/account/find/email")
    public String findAccountFromEmailForm() {
        return "/auth/find-account-email";
    }

    @GetMapping("/account/find/phone")
    public String findAccountFromPhoneForm() {
        return "/auth/find-account-phone";
    }
}