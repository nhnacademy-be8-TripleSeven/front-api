package com.tripleseven.frontapi.controller.store;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class StoreController {

    @GetMapping("/frontend/store/main")
    public String storeMain() {
        return "/store/main";
    }

    @GetMapping("/frontend/store/books/search")
    public String bookSearch() {
        return "/store/book/search";
    }

    @GetMapping("/frontend/store/books/{bookId}/details")
    public String bookDetails(@PathVariable Long bookId) {
        return "/store/book/detail";
    }

}
