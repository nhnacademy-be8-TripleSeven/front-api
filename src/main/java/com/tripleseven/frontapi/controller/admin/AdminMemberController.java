package com.tripleseven.frontapi.controller.admin;

import com.tripleseven.frontapi.dto.MemberDTO;
import com.tripleseven.frontapi.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/frontend")
public class AdminMemberController {

    private final AdminService adminService;

    @GetMapping("/members")
    public ModelAndView getMemberSearchPage(@RequestParam(defaultValue = "") String name,
                                      Pageable pageable,
                                      ModelAndView modelAndView) {


        Page<MemberDTO> members = adminService.getMembers(name, pageable);  // sort 매개변수를 전달합니다.
        modelAndView.addObject("page", members);
        modelAndView.addObject("searchName", name);
        modelAndView.addObject("sort", pageable.getSort().toString());
        modelAndView.setViewName("admin/user-list");
        return modelAndView;
    }

    @GetMapping("/members/grade")
    public String getMemberGradeSearchPage() {
        return "admin/member/search/grade";
    }
}
