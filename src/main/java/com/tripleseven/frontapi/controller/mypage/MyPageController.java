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

        // 포인트 조회
        int point = orderService.getPoints(userId);
        model.addAttribute("point", point);

        // 회원 정보 조회
        MemberDTO memberInfo = memberService.getMemberInfo(userId);
        model.addAttribute("member", memberInfo);

        // 멤버십 등급 정보 추출
        String membershipGrade = memberInfo.getMemberGrade();  // memberGrade 필드에서 멤버십 등급 추출
        model.addAttribute("membershipGrade", membershipGrade);

        return "my-page";
    }


}