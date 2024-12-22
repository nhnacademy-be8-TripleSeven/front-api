package com.tripleseven.frontapi.client;


import com.tripleseven.frontapi.domain.Book;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "book-coupon-api")
public interface BookApiFeignClient {

    @GetMapping("/books/monthly")
    List<Book> getMonthlyBooks();
}
