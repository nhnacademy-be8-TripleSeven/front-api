package com.tripleseven.frontapi.controller.admin;

import com.tripleseven.frontapi.dto.book.BookApiDTO;
import com.tripleseven.frontapi.dto.book.BookDTO;
import com.tripleseven.frontapi.dto.book.BookPageDTO;
import com.tripleseven.frontapi.dto.book.BookUpdateDTO;
import com.tripleseven.frontapi.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminBookController {

    private final BookService bookService;

    @GetMapping("/books/keyword/{keyword}")
    public String getBookSearchPage(@PathVariable String keyword, Pageable pageable, Model model) {
        BookPageDTO adminBooksByKeyword = bookService.getAdminBooksByKeyword(keyword, pageable);

        model.addAttribute("bookPageDTO", adminBooksByKeyword);

        return "/admin/book-list";
    }

    @GetMapping("/books/update/{id}")
    public String updateBookDetail(@PathVariable Long id, Model model){
        BookDTO bookById = bookService.getBookById(id);
        model.addAttribute("book", bookById);
        return "/admin/book-update";
    }

    @PostMapping("/books/updateBook")
    public BookDTO updateBook(@RequestBody BookUpdateDTO bookDTO) {
        return bookService.updateBook(bookDTO);
    }

    @PostMapping("/books/createBook")


    @GetMapping("/books/create")
    public String createBook() {return "/admin/book/create";}



    @GetMapping("/books/aladin/isbn/{isbn}")
    public String getAladinBookByIsbn(@PathVariable String isbn, Model model) {
        BookApiDTO aladinApiBook = bookService.getAladinApiBook(isbn);
        model.addAttribute("aladinBook", aladinApiBook);
        return "/admin/book/aladin";
    }

    @DeleteMapping("/books/delele/{bookId}")
    public String deleteBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);
        return "redirect:/admin/books/";
    }




}
