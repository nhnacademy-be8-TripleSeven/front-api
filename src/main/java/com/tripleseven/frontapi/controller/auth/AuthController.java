package com.tripleseven.frontapi.controller.auth;

import com.tripleseven.frontapi.client.MemberFeignClient;
import com.tripleseven.frontapi.dto.book.BookPageDTO;
import com.tripleseven.frontapi.dto.member.MemberAccountDto;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequiredArgsConstructor
public class AuthController {



    private final MemberFeignClient memberFeignClient;

    @GetMapping("/frontend/join")
    public String join() {

        return "auth/join";
    }

    @GetMapping("/frontend/login")
    public ModelAndView login(HttpServletRequest request, ModelAndView modelAndView) {

        modelAndView.setViewName("auth/login");
        return modelAndView;
    }

//    @GetMapping("/frontend/oauth2/authorization/payco")
//    public String paycoLoginRedirect() {
//        String redirectUri = "https://nhn24.store/payco/callback";
//        String encodedRedirectUri;
//        try {
//            encodedRedirectUri = URLEncoder.encode(redirectUri, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            throw new RuntimeException("Error encoding redirectUri", e);
//        }
//        String authorizationUri = "https://id.payco.com/oauth2.0/authorize"
//                + "?response_type=code"
//                + "&client_id=" + paycoClientId
//                + "&serviceProviderCode=FRIENDS"
//                + "&redirect_uri=" + encodedRedirectUri
//                + "&userLocale=ko_KR";
//
//        return "redirect:" + authorizationUri;
//    }

//    @GetMapping("/frontend/payco/callback")
//    public ModelAndView callbackPaycoLogin(@RequestParam String code, ModelAndView modelAndView) {
//        MemberAccountDto memberAccountDto = afterPaycoLoginService.savePaycoMemberDetail(code);
//        modelAndView.addObject("loginId", memberAccountDto.getLoginId());
//        modelAndView.setViewName("auth/payco/callback");
//
//        return modelAndView;
//    }

    @GetMapping("/frontend/find-account")
    public String findAccount() {
        return "auth/find-account";
    }

    @GetMapping("/frontend/account/find/email")
    public String findAccountFromEmailForm() {
        return "auth/find-account-email";
    }

    @GetMapping("/frontend/account/find/phone")
    public String findAccountFromPhoneForm() {
        return "auth/find-account-phone";
    }

    @GetMapping("/frontend/reset-password")
    public ModelAndView resetPasswordForm(@RequestParam String email, @RequestParam String code, ModelAndView modelAndView) {
        modelAndView.addObject("email", email);
        modelAndView.addObject("code", code);
        modelAndView.setViewName("auth/reset-password");
        return modelAndView;
    }

    @GetMapping("/frontend/active-account")
    public ModelAndView activeAccount(@RequestParam String email, @RequestParam String code, ModelAndView modelAndView) {
        memberFeignClient.verifyAccountActiveCode(email, code);
        modelAndView.addObject("success", true);
        modelAndView.setViewName("auth/unlock-account");
        return modelAndView;
    }

    @GetMapping("/frontend/admin/login")
    public String adminLogin(@RequestHeader(value = "X-USER",required = false)Long userId, Model model) {
        if (userId != null) {
            model.addAttribute("bookPageDTO", new BookPageDTO());
            return "admin/book-list";
        }

        return "admin/admin-login";
    }
}