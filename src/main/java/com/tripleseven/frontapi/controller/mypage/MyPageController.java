package com.tripleseven.frontapi.controller.mypage;

import com.tripleseven.frontapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MyPageController {
    private final OrderService orderService;

    @GetMapping("/mypage")
    public String getMyPage(Model model) {
        int point = orderService.getPoints();
        model.addAttribute("point", point);
        return "my-page";
    }
}
