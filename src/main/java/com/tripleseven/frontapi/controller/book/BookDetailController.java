package com.tripleseven.frontapi.controller.book;

import com.tripleseven.frontapi.dto.ReviewResponseDTO;
import com.tripleseven.frontapi.dto.SearchBookDetailDTO;
import com.tripleseven.frontapi.service.BookApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.format.DateTimeFormatter;

@Controller
@RequiredArgsConstructor
public class BookDetailController {

    private final BookApiService bookApiService;

    @GetMapping("/books/{bookId}")
    public String bookDetail(
            @PathVariable Long bookId,
            Model model) {
        SearchBookDetailDTO book = bookApiService.getBookDetail(bookId);
        book.setId(bookId);
        model.addAttribute("book", book);
        model.addAttribute("formattedPublishedDate",
                book.getPublishedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        return "order-detail"; // HTML 페이지 렌더링
    }

    @ResponseBody
    @GetMapping("/api/reviews/{bookId}")
    public Page<ReviewResponseDTO> getPagedReviews(
            @PathVariable Long bookId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bookApiService.getPagedReviewsByBookId(bookId, pageable);
    }
}
