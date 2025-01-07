package com.tripleseven.frontapi.controller.book;


import com.tripleseven.frontapi.dto.book.BookPageDetailResponseDTO;
import com.tripleseven.frontapi.dto.book.BookPageResponseDTO;
import com.tripleseven.frontapi.dto.book.CategoryBookSearchViewDTO;
import com.tripleseven.frontapi.dto.book.KeywordSearchBookViewDTO;
import com.tripleseven.frontapi.dto.book.TypeBookSearchViewDTO;
import com.tripleseven.frontapi.dto.category.CategoryDTO;
import com.tripleseven.frontapi.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class BookSearchController {

    private final BookService bookApiService;

    @GetMapping("/frontend/searchBook")
    public String bookSearch(
        @RequestParam(value = "keyword", defaultValue = " ") String term,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(value = "sortField", defaultValue = "publishDate") String sortField,
        @RequestParam(value = "sortDir", defaultValue = "desc") String sortDir,
        Model model) {

        Sort sort = Sort.unsorted();
        if(sortField != null) {
            sort = Sort.by(Sort.Order.by(sortField).with(Sort.Direction.fromString(sortDir)));
        }
        Pageable pageable =  PageRequest.of(page, size, sort);

        BookPageResponseDTO bookPageResponseDTO = bookApiService.searchBooks(term, pageable);

        int totalPages = (int) Math.ceil((double) bookPageResponseDTO.getTotalElements()/ size);
        // 페이지네이션 계산
        int startPage = (page / 5) * 5;
        int endPage = (int) Math.min(startPage + 4, totalPages - 1);
        KeywordSearchBookViewDTO searchBook = new KeywordSearchBookViewDTO(term, "searchBook",
            bookPageResponseDTO.getContent(), page, size, sortField, sortDir, bookPageResponseDTO.getTotalElements(),startPage, endPage);
        List<CategoryDTO> allCategories = bookApiService.getAllCategories();
        model.addAttribute("categories", allCategories);
        model.addAttribute("searchBook", searchBook);
        model.addAttribute("keyword", term);
        return "book-search";
    }


    @GetMapping("/frontend/typeBook")
    public String typeBookSearch(
        @RequestParam String type,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(value = "sortField", required = false) String sortField,
        @RequestParam(value = "sortDir", required = false) String sortDir,
        Model model) {
        BookPageDetailResponseDTO typeBookSearch = bookApiService.getTypeBookSearch(type, page,
            size, sortField, sortDir);
        // 페이지네이션 계산
        int startPage = page - (page % 5);  // 시작 페이지
        int endPage = Math.min(startPage + 4, typeBookSearch.getTotalPages() - 1);  // 종료 페이지

        TypeBookSearchViewDTO typeBookSearchViewDTO = new TypeBookSearchViewDTO(type,
            "typeBook",
            typeBookSearch.getContent(),
            page,
            size,
            sortField,
            sortDir,
            typeBookSearch.getTotalElements(),
            startPage,
            endPage);

        List<CategoryDTO> allCategories = bookApiService.getAllCategories();
        model.addAttribute("categories", allCategories);
        model.addAttribute("searchBook", typeBookSearchViewDTO);

        return "book-search";
    }




    @GetMapping("/frontend/categorySearch")
    public String categorySearch(@RequestParam long id, @PageableDefault(size = 10, page = 0) Pageable pageable, Model model) {
        BookPageDetailResponseDTO categorySearch = bookApiService.getCategorySearch(id, pageable);

        List<CategoryDTO> allCategories = bookApiService.getAllCategories();
        categorySearch.addPath("categorySearch");
        model.addAttribute("categories", allCategories);
        model.addAttribute("searchBook", categorySearch);
        return "book-search";
    }
}
