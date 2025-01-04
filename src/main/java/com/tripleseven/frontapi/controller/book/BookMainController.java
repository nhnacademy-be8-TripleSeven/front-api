package com.tripleseven.frontapi.controller.book;

import com.tripleseven.frontapi.dto.book.BookDetailResponseDTO;
import com.tripleseven.frontapi.service.BookService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class BookMainController {
    private final BookService bookApiService;

    private final String BLOG_BEST = "blogBest";
    private final String ITEM_EDITOR_CHOICE = "itemEditorChoice";
    private final String EBOOK = "ebook";
    private final String FOREIGN = "foreign";



    @GetMapping(value = {"/frontend/", "/frontend/main"})
    public String getMonthlyBooks(HttpServletRequest request, Model model) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null) {
            ip = request.getRemoteAddr();
        }
        model.addAttribute("ip", ip);
        model.addAttribute("url", request.getRequestURI());
        List<BookDetailResponseDTO> dto = bookApiService.fetchMonthlyBooks();
        List<BookDetailResponseDTO> blogBest = bookApiService.fetchBooksByType(
            BLOG_BEST);
        List<BookDetailResponseDTO> itemEditorChoice = bookApiService.fetchBooksByType(
            ITEM_EDITOR_CHOICE);
        List<BookDetailResponseDTO> ebook = bookApiService.fetchBooksByType(EBOOK);
        List<BookDetailResponseDTO> foreign = bookApiService.fetchBooksByType(
            FOREIGN);
        model.addAttribute("bestBooks", dto);
        model.addAttribute("blogBest", blogBest);
        model.addAttribute("itemEditorChoice", itemEditorChoice);
        model.addAttribute("ebook", ebook);
        model.addAttribute("foreign", foreign);


        return "main";
    }


}