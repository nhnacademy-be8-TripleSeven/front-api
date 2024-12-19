package com.tripleseven.frontapi.controller.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/frontend/auth")
public class AuthController {

    @GetMapping("/join")
    public String join() {

        return "/auth/join";
    }

    @GetMapping("/login")
    public String login() {
        return "/auth/login";
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