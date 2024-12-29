package com.tripleseven.frontapi.controller.book;


import com.tripleseven.frontapi.dto.BookDetailResponseDTO;
import com.tripleseven.frontapi.dto.BookSearchResponseDTO;
import com.tripleseven.frontapi.service.BookApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class BookSearchController {

    private final BookApiService bookApiService;

    @GetMapping("/searchBook")
    public String bookSearch(
        @RequestParam(value = "searchTerm", required = false) String term,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(value = "sortField", defaultValue = "publishDate") String sortField,
        @RequestParam(value = "sortDir", defaultValue = "desc") String sortDir,
        Model model) {

        term = (term == null || term.trim().isEmpty()) ? "default" : term;



        Page<BookSearchResponseDTO> searchBooksPage = bookApiService.searchBooks(term, page, size, sortField, sortDir);

        model.addAttribute("path", "searchBook");
        model.addAttribute("searchBooks", searchBooksPage.getContent());
        model.addAttribute("currentPage", searchBooksPage.getNumber());
        model.addAttribute("totalPages", searchBooksPage.getTotalPages());
        model.addAttribute("searchTerm", term);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);

        return "book-search";
    }


    @GetMapping("/typeBook")
    public String typeBookSearch(
        @RequestParam String type,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(value = "sortField", defaultValue = "publishDate") String sortField,
        @RequestParam(value = "sortDir", defaultValue = "asc") String sortDir,
        Model model) {
        Page<BookDetailResponseDTO> bookDetailResponseDTOS = bookApiService.getTypeBookSearch(type, page, size, sortField, sortDir);


        model.addAttribute("type", type);
        model.addAttribute("path", "typeBook");
        model.addAttribute("searchBooks", bookDetailResponseDTOS.getContent());
        model.addAttribute("currentPage", bookDetailResponseDTOS.getNumber());
        model.addAttribute("totalPages", bookDetailResponseDTOS.getTotalPages());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);


        return "book-search";
    }
}
