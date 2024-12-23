package com.tripleseven.frontapi.client;

import com.tripleseven.frontapi.dto.BookDetailResponseDTO;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "book-coupon-api")
public interface BookFeignClient {
    @GetMapping("/api/books/monthly")
    List<BookDetailResponseDTO> getMonthlyBooks();

    @GetMapping("/api/books/type/{type}")
    List<BookDetailResponseDTO> getBooksByType(@PathVariable("type") String type);
}
