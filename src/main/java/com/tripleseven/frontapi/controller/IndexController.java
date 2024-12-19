package com.tripleseven.frontapi.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class IndexController {
    @GetMapping(value = {"/main.html", "/"})
    public String index(HttpServletRequest request, ModelMap model) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null) {
            ip = request.getRemoteAddr();
        }
        model.addAttribute("ip", ip);
        model.addAttribute("url", request.getRequestURI());
        return "pay-user";
    }
}
