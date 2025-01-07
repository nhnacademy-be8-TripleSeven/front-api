package com.tripleseven.frontapi.controller.admin;

import com.tripleseven.frontapi.dto.category.CategoryDTO;
import com.tripleseven.frontapi.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class AdminCategoryController {

    private final BookService bookService;

    // 카테고리 필터링
    @GetMapping("/admin/books/categories")
    public String adminCategoryByLevel(@RequestParam(required = true, defaultValue = "1", value = "level") Integer level, Model model) {
        List<CategoryDTO> categoryByLevel = bookService.getCategroyByLevel(level);
        model.addAttribute("categories", categoryByLevel);
        model.addAttribute("selectedLevel", level); // 선택된 레벨을 뷰에 전달
        return "admin/category";
    }

    @PostMapping("/admin/books/categoryCreate")
    public String adminCategoryCreate(@RequestBody List<CategoryDTO> categoryDTO) {
        bookService.createCategory(categoryDTO);
        return "redirect:/admin/frontend/books";
    }

    @DeleteMapping("/admin/books/categoryDelete")
    public String adminCategoryDelete(@RequestParam("id") Long id) {
        bookService.deleteCategory(id);
        return "redirect:/admin/frontend/category";
    }

    @GetMapping("/admin/frontend/category")
    public String categoryView(){
        return "admin/category";
    }
}
