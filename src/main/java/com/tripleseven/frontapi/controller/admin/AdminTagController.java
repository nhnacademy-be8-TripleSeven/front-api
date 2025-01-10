package com.tripleseven.frontapi.controller.admin;


import com.tripleseven.frontapi.dto.tag.TagResponseDto;
import com.tripleseven.frontapi.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminTagController {

    private final BookService bookService;

    @GetMapping("/admin/frontend/tags")
    public String frontendTags(Model model) {
        List<TagResponseDto> tags = bookService.getAllTags();
        model.addAttribute("tags", tags);
        return "admin/tag-register";
    }
}
