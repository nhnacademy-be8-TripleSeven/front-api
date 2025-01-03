package com.tripleseven.frontapi.client;

import com.tripleseven.frontapi.dto.book.BookDetailResponseDTO;
import com.tripleseven.frontapi.dto.book.BookPageResponseDTO;


import com.tripleseven.frontapi.dto.likes.LikesResponseDTO;

import com.tripleseven.frontapi.dto.review.ReviewRequestDTO;
import com.tripleseven.frontapi.dto.coupon.CouponDetailsDTO;

import org.springframework.data.domain.Pageable;
import com.tripleseven.frontapi.dto.coupon.CouponPolicyRequestDTO;
import com.tripleseven.frontapi.dto.coupon.CouponPolicyResponseDTO;
import com.tripleseven.frontapi.dto.book.BookPageDetailResponseDTO;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import com.tripleseven.frontapi.dto.review.ReviewResponseDTO;
import com.tripleseven.frontapi.dto.book.BookDetailViewDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@FeignClient(name = "book-coupon-api")
public interface BookFeignClient {
    @GetMapping("/books/monthly")
    List<BookDetailResponseDTO> getMonthlyBooks();

    @GetMapping("/books/type/{type}")
    List<BookDetailResponseDTO> getBooksByType(@PathVariable("type") String type);

    @GetMapping("/books/term/{term}")
    BookPageResponseDTO getBooksByTerm(
        @PathVariable("term") String term,
        Pageable pageable
    );
  
    @GetMapping("/books/{bookId}")
    BookDetailViewDTO getBookDetail(@PathVariable Long bookId);
  
    @GetMapping("/books/category")
    BookPageResponseDTO getCategoryBooks(
        @RequestParam String keyword,
        @RequestParam List<String> categories,
        @RequestParam int page,
        @RequestParam int size
    );
  
    @GetMapping("/books/typeSearch/{type}")
    BookPageDetailResponseDTO getTypeSearchBooks(
        @PathVariable("type") String type,
        Pageable pageable
    );
    @GetMapping("/books/categories/{categories}/keyword/{keyword}")
    BookPageDetailResponseDTO getCategoriesSearchBooks(
        @PathVariable("categories") List<String> categories,
        @PathVariable("keyword") String keyword,
        Pageable pageable
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


    @GetMapping("/api/coupons")
    List<CouponDetailsDTO> getAllCoupons(@RequestHeader("X-USER") Long userId,
                                         @RequestParam(required = false) String keyword,
                                         @RequestParam(required = false) String startDate,
                                         @RequestParam(required = false) String endDate);

    @GetMapping("/api/coupons/used")
    List<CouponDetailsDTO> getUsedCoupons(@RequestHeader("X-USER") Long userId,
                                          @RequestParam(required = false) String keyword,
                                          @RequestParam(required = false) String startDate,
                                          @RequestParam(required = false) String endDate);

    @GetMapping("/api/reviews/{bookId}/paged")
    Page<ReviewResponseDTO> getPagedReviewsByBookId(
            @PathVariable Long bookId,
            @RequestParam("page") int page,
            @RequestParam("size") int size);

    @GetMapping("/api/reviews/{bookId}/all")
    List<ReviewResponseDTO> getAllReviewByBookId(@PathVariable Long bookId);

    @GetMapping("/api/reviews/{bookId}/user")
    ReviewResponseDTO getUserReviewForBook(@PathVariable("bookId") Long bookId, @RequestHeader("X-USER") Long userId);

    @PostMapping("/api/reviews")
    void addReview(@RequestHeader("X-USER") Long userId, @RequestBody ReviewRequestDTO requestDto);

    @GetMapping("/api/likes")
    List<LikesResponseDTO> getAllLikesByUserId(@RequestHeader("X-USER") Long userId, @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size);

    @GetMapping("/api/likes/search")
    List<LikesResponseDTO> searchLikesByUserIdAndKeyword(
            @RequestHeader("X-USER") Long userId,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    );
    @GetMapping("/api/likes/{bookId}/status")
    boolean isLiked(@RequestHeader("X-USER") Long userId, @PathVariable Long bookId);

    @PostMapping("/api/likes/{bookId}")
    void addLikes(@PathVariable Long bookId, @RequestHeader("X-USER") Long userId);

    @DeleteMapping("/api/likes/{bookId}")
    void deleteLikes(@PathVariable Long bookId, @RequestHeader("X-USER") Long userId);
}
