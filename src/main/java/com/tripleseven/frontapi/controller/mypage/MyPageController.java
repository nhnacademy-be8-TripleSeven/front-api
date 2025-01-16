package com.tripleseven.frontapi.controller.mypage;

import com.tripleseven.frontapi.dto.AddressDTO;
import com.tripleseven.frontapi.dto.MemberDTO;
import com.tripleseven.frontapi.service.MemberService;
import com.tripleseven.frontapi.service.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class MyPageController {
    private final OrderService orderService;
    private final MemberService memberService;

    @GetMapping("/frontend/mypage")
    public String getMyPage(
            @RequestHeader(value = "X-USER", required = false) Long userId,
            Model model) {
        if (Objects.isNull(userId)) {
            return "auth/login";
        }

        int point = orderService.getPoints(userId);
        model.addAttribute("point", point);
        return "my-page";
    }


    @GetMapping("/frontend/member/member-edit")
    public String getMemberEditPage(
            @RequestHeader(value = "X-USER", required = false) Long userId,
            Model model) {
        if (Objects.isNull(userId)) {
            return "auth/login";
        }

        MemberDTO memberInfo = memberService.getMemberInfo(userId);
        model.addAttribute("member", memberInfo);
        return "member/my-page/member-edit";
    }

    @GetMapping("/frontend/member/address-manage")
    public String getAddressManagePage(
            @RequestHeader(value = "X-USER", required = false) Long userId,
            Model model) {
        if (Objects.isNull(userId)) {
            return "auth/login";
        }

        List<AddressDTO> addresses = memberService.getAddresses(userId);
        model.addAttribute("addresses", addresses);
        return "member/my-page/address-manage"; 
    }
}