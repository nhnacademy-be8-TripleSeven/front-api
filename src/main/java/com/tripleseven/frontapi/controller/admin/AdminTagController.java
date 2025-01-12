package com.tripleseven.frontapi.controller.admin;


import com.tripleseven.frontapi.dto.book.BookTagResponseDTO;
import com.tripleseven.frontapi.dto.tag.TagResponseDto;
import com.tripleseven.frontapi.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/admin/frontend/book_tags")
    public String bookTags(@RequestParam(name="bookId", required=false) Long bookId,
                           Model model) {
        // bookId가 null이거나 0 이하라면, 빈 리스트 반환
        if (bookId == null || bookId <= 0) {
            model.addAttribute("tags", List.of());
            model.addAttribute("bookId", null);
        } else {
            // 실제 bookService에서 도서 ID에 따른 태그 목록을 가져오는 메서드를 호출
            List<BookTagResponseDTO> tags = bookService.getTagsByBookId(bookId);
            model.addAttribute("tags", tags);
            model.addAttribute("bookId", bookId);
        }

        return "admin/book-tag-register";  // 아래 HTML(Thymeleaf) 파일을 렌더링
    }
}
