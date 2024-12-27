package com.tripleseven.frontapi.controller.book;


import com.tripleseven.frontapi.dto.BookSearchResponseDTO;
import com.tripleseven.frontapi.service.BookApiService;
import jakarta.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class BookSearchController {

    private final BookApiService bookApiService;

    @GetMapping("/searchBook")
    public String bookSearch(@RequestParam(value = "searchTerm", required = false) String term, Model model) {
        List<BookSearchResponseDTO> searchBooks = bookApiService.searchBooks(term);
        model.addAttribute("searchBooks", searchBooks);

        for (BookSearchResponseDTO searchBook : searchBooks) {
            String bookCreators = searchBook.getBookcreator();

        }
        return "book-search";
    }
}
