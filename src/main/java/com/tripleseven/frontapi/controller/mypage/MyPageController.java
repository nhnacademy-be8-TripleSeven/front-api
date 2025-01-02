package com.tripleseven.frontapi.controller.mypage;

import com.tripleseven.frontapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class MyPageController {
    private final OrderService orderService;

    @GetMapping("/frontend/mypage")
    public String getMyPage(
            @RequestHeader(value = "X-USER", required = false) Long userId,
            Model model) {
        if(Objects.isNull(userId)){
            return "redirect:/frontend/login";
        }

        int point = orderService.getPoints(userId);

        model.addAttribute("point", point);
        return "my-page";
    }
}
