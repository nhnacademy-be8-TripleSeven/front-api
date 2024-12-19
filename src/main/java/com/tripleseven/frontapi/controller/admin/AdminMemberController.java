package com.tripleseven.frontapi.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/frontend")
public class AdminMemberController {

    @GetMapping("/members")
    public String getMemberNameSearchPage() {
        return "/admin/member/search/name";
    }

    @GetMapping("/members/grade")
    public String getMemberGradeSearchPage() {
        return "/admin/member/search/grade";
    }
}
