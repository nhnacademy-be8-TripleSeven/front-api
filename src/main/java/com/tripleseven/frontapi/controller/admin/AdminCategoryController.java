package com.tripleseven.frontapi.controller.admin;

import com.tripleseven.frontapi.dto.category.CategoryDTO;
import com.tripleseven.frontapi.dto.category.PageCategoryDTO;
import com.tripleseven.frontapi.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class AdminCategoryController {

    private final BookService bookService;

    // 카테고리 필터링
    @GetMapping("/admin/frontend/books/categories")
    public String adminCategoryByLevel(@RequestParam(required = true, defaultValue = "1", value = "level") int level,
        @PageableDefault(size = 5, page = 0) Pageable pageable,
        Model model) {
        PageCategoryDTO categroyByLevel = bookService.getCategroyByLevel(level, pageable);

        model.addAttribute("categoryPage", new PageImpl<>(categroyByLevel.getContent(), pageable, categroyByLevel.getTotalElements()));
        model.addAttribute("selectedLevel", level);
        return "admin/category-list";
    }

//    @PostMapping("/admin/books/categoryCreate")
//    public String adminCategoryCreate(@RequestBody List<CategoryDTO> categoryDTO) {
//        bookService.createCategory(categoryDTO);
//        return "redirect:/admin/frontend/books";
//    }
//
//    @DeleteMapping("/admin/books/categoryDelete")
//    public String adminCategoryDelete(@RequestParam("id") Long id) {
//        bookService.deleteCategory(id);
//        return "redirect:/admin/frontend/category";
//    }

    @GetMapping("/admin/frontend/category")
    public String categoryView(){
        return "admin/category";
    }

    @GetMapping("/admin/frontend/category-list")
    public String categoryListView(Model model) {
        model.addAttribute("categories", new CategoryDTO());
        model.addAttribute("page", 0);
        model.addAttribute("selectedLevel", 1);
        return "admin/category-list";
    }
}
