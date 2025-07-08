package com.tripleseven.frontapi.controller.book;


import com.tripleseven.frontapi.dto.book.BookPageDetailResponseDTO;
import com.tripleseven.frontapi.dto.book.BookPageResponseDTO;
import com.tripleseven.frontapi.dto.book.CategoryBookSearchViewDTO;
import com.tripleseven.frontapi.dto.book.KeywordSearchBookViewDTO;
import com.tripleseven.frontapi.dto.book.TypeBookSearchViewDTO;
import com.tripleseven.frontapi.dto.category.CategoryResponseDTO;
import com.tripleseven.frontapi.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class BookSearchController {

    private final BookService bookApiService;

    @GetMapping("/frontend/search-book")
    public String bookSearch(
        @RequestParam(value = "search", defaultValue = " ") String search,
        @PageableDefault Pageable pageable,
        Model model) {


        BookPageResponseDTO bookPageResponseDTO = bookApiService.searchBooks(search, pageable);

        int startPage = pageable.getPageNumber() - (pageable.getPageNumber() % 5);  // 시작 페이지
        int endPage = Math.min(startPage + 4, bookPageResponseDTO.getTotalPages() - 1);  // 종료 페이지
        KeywordSearchBookViewDTO searchBook = new KeywordSearchBookViewDTO(
            search,
            "search-book",
            new PageImpl<>(
                bookPageResponseDTO.getContent(),
                pageable,
                bookPageResponseDTO.getTotalPages()),
            startPage,
            endPage);
        List<CategoryResponseDTO> allCategories = bookApiService.getAllCategories();
        model.addAttribute("categories", allCategories);
        model.addAttribute("searchBook", searchBook);
        return "book-search";
    }


    @GetMapping("/frontend/type-book")
    public String typeBookSearch(
        @RequestParam("search") String search,
        @PageableDefault(sort = "title", direction = Direction.DESC) Pageable pageable,
        Model model) {
        BookPageDetailResponseDTO typeBookSearch = bookApiService.getTypeBookSearch(search, pageable);
        // 페이지네이션 계산
        int startPage = pageable.getPageNumber() - (pageable.getPageNumber() % 5);  // 시작 페이지
        int endPage = Math.min(startPage + 4, typeBookSearch.getTotalPages() - 1);  // 종료 페이지

        TypeBookSearchViewDTO typeBookSearchViewDTO = new TypeBookSearchViewDTO(search,
            "type-book",
            new PageImpl<>(typeBookSearch.getContent(), pageable, typeBookSearch.getTotalElements()),
            startPage,
            endPage);

        List<CategoryResponseDTO> allCategories = bookApiService.getAllCategories();
        model.addAttribute("categories", allCategories);
        model.addAttribute("searchBook", typeBookSearchViewDTO);

        return "book-search";
    }




    @GetMapping("/frontend/category-search")
    public String categorySearch(
        @RequestParam("search") Long search,
        @PageableDefault(sort = "title", direction = Direction.DESC) Pageable pageable,
        Model model
        ) {

        BookPageDetailResponseDTO categorySearch = bookApiService.getCategorySearch(search, pageable);
        // 페이지네이션 계산
        int startPage = pageable.getPageNumber() - (pageable.getPageNumber() % 5);  // 시작 페이지
        int endPage = Math.min(startPage + 4, categorySearch.getTotalPages() - 1);  // 종료 페이지

        CategoryBookSearchViewDTO viewBook = new CategoryBookSearchViewDTO(
            search,
            "category-search",
            new PageImpl<>(categorySearch.getContent(), pageable, categorySearch.getTotalElements()),
            startPage,
            endPage
        );

        List<CategoryResponseDTO> allCategories = bookApiService.getAllCategories();
        categorySearch.addPath("categorySearch");
        model.addAttribute("categories", allCategories);
        model.addAttribute("searchBook", viewBook);
        return "book-search";
    }
}
