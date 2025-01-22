
package com.tripleseven.frontapi.controller.address;

import com.tripleseven.frontapi.dto.MemberDTO;
import com.tripleseven.frontapi.service.MemberService;
import jakarta.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class AddressController {

    private final MemberService memberService;


    @GetMapping("/frontend/member/edit")
    public String getMemberEditPage(@RequestHeader(value = "X-USER", required = false) Long userId,
                                    @RequestParam(value = "check", required = false) Boolean check, Model model) {
        if (Objects.isNull(userId)) {
            System.out.println("User ID is null. Redirecting to login.");
            return "/auth/login";
        }
        model.addAttribute("userId", userId);

        if (check == null) {
            return "/member/pwd-check";
        }

        MemberDTO member = memberService.getMemberInfoWithDefaultAddress(userId);
        model.addAttribute("member", member);
        return "/member/member-edit";
    }


    @GetMapping("/frontend/member/address-manage")
    public String getAddressManagePage(@RequestHeader(value = "X-USER", required = false) Long userId,
                                       @RequestParam(value = "check", required = false) Boolean check, Model model) {
        if (Objects.isNull(userId)) {
            return "/auth/login";
        }
        model.addAttribute("userId", userId);
        if (check == null) {
            return "/member/pwd-check2";
        }
        MemberDTO member = memberService.getMemberInfo(userId);
        model.addAttribute("addresses", member.getAddresses());
        return "/member/address-manage";
    }


    @GetMapping("/frontend/member/pwd-check")
    public String getPasswordCheckPage(@RequestHeader(value = "X-USER", required = false) Long userId
            , Model model) {
        if (userId == null) {
            return "redirect:/auth/login";
        }
        return "/member/pwd-check";
    }






}