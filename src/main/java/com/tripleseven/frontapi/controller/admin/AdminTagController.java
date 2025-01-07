package com.tripleseven.frontapi.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AdminTagController {

    @GetMapping("/admin/frontend/tags")
    public String frontendTags(Model model) {
        return "/admin/tag-register";
    }
}
