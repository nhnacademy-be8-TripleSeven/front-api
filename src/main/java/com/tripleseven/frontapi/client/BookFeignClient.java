package com.tripleseven.frontapi.client;

import com.tripleseven.frontapi.dto.BookDetailResponseDTO;
import com.tripleseven.frontapi.dto.BookPageResponseDTO;
import com.tripleseven.frontapi.dto.BookSearchResponseDTO;
import java.util.List;
import com.tripleseven.frontapi.dto.coupon.CouponPolicyRequestDTO;
import com.tripleseven.frontapi.dto.coupon.CouponPolicyResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/books/term/{term}")
    BookPageResponseDTO getBooksByTerm(
        @PathVariable("term") String term,
        @RequestParam("page") int page,
        @RequestParam("size") int size
    );

    @GetMapping("/books/{bookId}")
    SearchBookDetailDTO getBookDetail(@PathVariable Long bookId);

    @GetMapping("/books/category")
    BookPageResponseDTO getCategoryBooks(
        @RequestParam String keyword,
        @RequestParam List<String> categories,
        @RequestParam int page,
        @RequestParam int size
    );

    @GetMapping("/books/typeSearch/{type}")
    BookPageResponseDTO getTypeSearchBooks(
        @PathVariable("type") String type,
        @RequestParam("page") int page,
        @RequestParam("size") int size
    );


    @PostMapping("/admin/coupon-policies")
    CouponPolicyResponseDTO createCouponPolicy(@RequestBody CouponPolicyRequestDTO request);

    @GetMapping("/admin/coupon-policies")
    List<CouponPolicyResponseDTO> getAllCouponPolicies();

    @GetMapping("/admin/coupon-policies/search")
    List<CouponPolicyResponseDTO> searchCouponPoliciesByName(@RequestParam String query);

    @GetMapping("/admin/coupon-policies/{id}")
    CouponPolicyResponseDTO getCouponPolicyById(@PathVariable Long id);

    @PutMapping("/admin/coupon-policies/{id}")
    CouponPolicyResponseDTO updateCouponPolicy(@PathVariable Long id, @RequestBody CouponPolicyRequestDTO request);

    @DeleteMapping("/admin/coupon-policies/{id}")
    void deleteCouponPolicy(@PathVariable Long id);

  
  
    @GetMapping("/api/reviews/{bookId}/paged")
    Page<ReviewResponseDTO> getPagedReviewsByBookId(
            @PathVariable Long bookId,
            @RequestParam("page") int page,
            @RequestParam("size") int size);

      @GetMapping("/api/reviews/{bookId}/all")
    List<ReviewResponseDTO> getAllReviewByBookId(@PathVariable Long bookId);
}
