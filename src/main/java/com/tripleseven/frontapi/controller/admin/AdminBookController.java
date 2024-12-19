package com.tripleseven.frontapi.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/frontend")
public class AdminBookController {

    @GetMapping("/book/search")
    public String getBookSearchPage() {
        return "/admin/book/search";
    }

    @GetMapping("/book/update")
    public String updateBookDetail() {
        return "/admin/book/update";
    }
}
