package com.tripleseven.frontapi.client;

import com.tripleseven.frontapi.dto.BookDetailResponseDTO;
import java.util.List;

import com.tripleseven.frontapi.dto.ReviewResponseDTO;
import com.tripleseven.frontapi.dto.SearchBookDetailDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "book-coupon-api")
public interface BookFeignClient {
    @GetMapping("/books/monthly")
    List<BookDetailResponseDTO> getMonthlyBooks();

    @GetMapping("/books/type/{type}")
    List<BookDetailResponseDTO> getBooksByType(@PathVariable("type") String type);

      @GetMapping("books/{bookId}")
    SearchBookDetailDTO getBookDetail(@PathVariable Long bookId);

    @GetMapping("/api/reviews/{bookId}/paged")
    Page<ReviewResponseDTO> getPagedReviewsByBookId(
            @PathVariable Long bookId,
            @RequestParam("page") int page,
            @RequestParam("size") int size);

    @GetMapping("/api/reviews/{bookId}/all")
    List<ReviewResponseDTO> getAllReviewByBookId(@PathVariable Long bookId);
}
