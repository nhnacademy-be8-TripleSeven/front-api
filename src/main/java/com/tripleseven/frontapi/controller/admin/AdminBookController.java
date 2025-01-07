package com.tripleseven.frontapi.controller.admin;

import com.tripleseven.frontapi.dto.book.BookAladinDTO;
import com.tripleseven.frontapi.dto.book.BookApiDTO;
import com.tripleseven.frontapi.dto.book.BookCreateDTO;
import com.tripleseven.frontapi.dto.book.BookDTO;
import com.tripleseven.frontapi.dto.book.BookPageDTO;
import com.tripleseven.frontapi.dto.book.BookUpdateDTO;
import com.tripleseven.frontapi.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

@Slf4j
@CrossOrigin(origins = "http://localhost:8080")
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminBookController {

    private final BookService bookService;

    @GetMapping("/frontend/books")
    public String frontendBooks(Model model) {
        model.addAttribute("bookPageDTO", new BookPageDTO());
        return "admin/book-list";
    }

    @GetMapping("/frontend/books/keyword")
    public String getBookSearchPage(@RequestParam String keyword, Pageable pageable, Model model) {
        BookPageDTO adminBooksByKeyword = bookService.getAdminBooksByKeyword(keyword, pageable);

        model.addAttribute("bookPageDTO", adminBooksByKeyword);

        return "admin/book-list";
    }

    @GetMapping("/frontend/books/update/{id}")
    public String updateBookDetail(@PathVariable Long id, Model model){
        BookDTO bookById = bookService.getBookById(id);
        model.addAttribute("book", bookById);
        return "admin/book-update";
    }

    @PostMapping("/books/updateBook")
    public String updateBook(@ModelAttribute BookUpdateDTO bookDTO) {
        log.info("update book {}", bookDTO);
        bookService.updateBook(bookDTO);
        log.info("Book updated");
        return "redirect:/admin/frontend/books";
    }

    @PostMapping("/books/createBook")
    public String createBook(@ModelAttribute BookCreateDTO bookDTO) {
        log.info("created start");
        bookService.createBook(bookDTO);
        log.info("Book created");
        return "redirect:/admin/frontend/books";
    }


    @GetMapping("/frontend/books/create")
    public String createBook(Model model) {
        model.addAttribute("book", new BookApiDTO());
        return "admin/book-create";
    }



    @GetMapping("/frontend/books/aladin")
    public String getAladinBookByIsbn(@RequestParam("isbn") String isbn, Model model) {
        BookAladinDTO aladinApiBook = bookService.getAladinApiBook(isbn);
        model.addAttribute("book", aladinApiBook);
        return "/admin/book-create";
    }

    @DeleteMapping("/books/delete/{bookId}")
    public String deleteBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);
        return "redirect:/admin/frontend/books";
    }





}
