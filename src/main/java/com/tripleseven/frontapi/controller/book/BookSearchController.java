package com.tripleseven.frontapi.controller.book;


import com.tripleseven.frontapi.dto.BookDetailResponseDTO;
import com.tripleseven.frontapi.dto.BookSearchResponseDTO;
import com.tripleseven.frontapi.dto.CategoryBookSearchViewDTO;
import com.tripleseven.frontapi.dto.KeywordSearchBookViewDTO;
import com.tripleseven.frontapi.dto.TypeBookSearchViewDTO;
import com.tripleseven.frontapi.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class BookSearchController {

    private final BookService bookApiService;

    @GetMapping("/searchBook")
    public String bookSearch(
        @RequestParam(value = "keyword", defaultValue = "없음") String term,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(value = "sortField", defaultValue = "publishDate") String sortField,
        @RequestParam(value = "sortDir", defaultValue = "desc") String sortDir,
        Model model) {

        term = (term == null || term.trim().isEmpty()) ? "default" : term;

        List<BookSearchResponseDTO> searchBooksPage = bookApiService.searchBooks(term, page, size, sortField, sortDir);

        KeywordSearchBookViewDTO searchBook = new KeywordSearchBookViewDTO(term, "searchBook",
            searchBooksPage, page, size, sortField, sortDir);
        model.addAttribute("searchBook", searchBook);

        return "book-search";
    }


    @GetMapping("/typeBook")
    public String typeBookSearch(
        @RequestParam String type,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(value = "sortField", defaultValue = "publishDate") String sortField,
        @RequestParam(value = "sortDir", defaultValue = "desc") String sortDir,
        Model model) {
        List<BookDetailResponseDTO> bookDetailResponseDTOS = bookApiService.getTypeBookSearch(type, page, size, sortField, sortDir);

        TypeBookSearchViewDTO typeBookSearchViewDTO = new TypeBookSearchViewDTO(type, "typeBook",
            bookDetailResponseDTOS, page, size, sortField, sortDir);

        model.addAttribute("searchBook", typeBookSearchViewDTO);

        return "book-search";
    }

    @GetMapping("/categorySearch")
    public String categorySearch(
        @RequestParam(value = "categories", defaultValue = "국내도서") List<String> categories,
        @RequestParam(value = "keyword", defaultValue = " ") String keyword,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(value = "sortField", defaultValue = "publishDate") String sortField,
        @RequestParam(value = "sortDir", defaultValue = "desc") String sortDir,
        Model model
    ){

        List<BookDetailResponseDTO> categorySearchBook = bookApiService.getCategorySearchBook(
            categories, keyword, page, size, sortField, sortDir);

        CategoryBookSearchViewDTO bookSearchViewDTO = new CategoryBookSearchViewDTO(keyword, categories, categorySearchBook,"categorySearch", page, size, sortField, sortDir);

        model.addAttribute("searchBook", bookSearchViewDTO);



        return "book-search";
    }
}
