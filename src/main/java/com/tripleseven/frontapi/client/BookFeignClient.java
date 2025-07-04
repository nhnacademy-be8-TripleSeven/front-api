package com.tripleseven.frontapi.client;


import com.tripleseven.frontapi.dto.book.BookApiDTO;
import com.tripleseven.frontapi.dto.book.BookCreateDTO;
import com.tripleseven.frontapi.dto.book.BookDTO;
import com.tripleseven.frontapi.dto.book.BookDetailResponseDTO;
import com.tripleseven.frontapi.dto.book.BookPageDTO;
import com.tripleseven.frontapi.dto.book.BookPageResponseDTO;


import com.tripleseven.frontapi.dto.book.BookUpdateDTO;
import com.tripleseven.frontapi.dto.book_creator.BookCreatorDTO;

import com.tripleseven.frontapi.dto.category.CategoryDTO;


import com.tripleseven.frontapi.dto.book.*;


import com.tripleseven.frontapi.dto.category.CategoryLevelDTO;
import com.tripleseven.frontapi.dto.category.CategoryResponseDTO;
import com.tripleseven.frontapi.dto.category.CategorySearchDTO;
import com.tripleseven.frontapi.dto.category.PageCategoryDTO;
import com.tripleseven.frontapi.dto.coupon.*;

import com.tripleseven.frontapi.dto.likes.LikesResponseDTO;

import com.tripleseven.frontapi.dto.review.ReviewRequestDTO;

import com.tripleseven.frontapi.dto.tag.TagResponseDto;
import org.springframework.data.domain.Pageable;
import com.tripleseven.frontapi.dto.coupon.CouponPolicyRequestDTO;
import com.tripleseven.frontapi.dto.coupon.CouponPolicyResponseDTO;
import com.tripleseven.frontapi.dto.book.BookPageDetailResponseDTO;
import org.springframework.data.domain.Sort;

import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.*;
import com.tripleseven.frontapi.dto.review.ReviewResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "book-coupon-api")
public interface BookFeignClient {
    @GetMapping("/books/monthly")
    List<BookDetailResponseDTO> getMonthlyBooks();

    @GetMapping("/books/type/{type}")
    List<BookDetailResponseDTO> getBooksByType(@PathVariable("type") String type);

    @GetMapping("/books/search/{term}")
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

    @GetMapping("/books/type-search/{type}")
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



    @GetMapping("/admin/coupons/book-search")
    List<BookSearchDTO> searchBooksByName(@RequestParam("query") String query);

    @GetMapping("/admin/coupons/category-search")
    List<CategorySearchDTO> searchCategoriesByName(@RequestParam("query") String query);

    @PostMapping("/admin/coupons/create-and-assign")
    List<CouponAssignResponseDTO> createAndAssignCoupons(@RequestBody CouponCreationAndAssignRequestDTO request);

    @PostMapping("/admin/coupons/bulk")
    BulkCouponCreationResponseDTO createCouponsInBulk(@RequestBody CouponBulkCreationRequestDTO request);

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

    @GetMapping("/api/coupons/unused")
    List<CouponDetailsDTO> getUnusedCoupons(@RequestHeader("X-USER") Long userId,
                                            @RequestParam(required = false)String keyword,
                                            @RequestParam(required = false)String startDate,
                                            @RequestParam(required = false)String endDate);

    @GetMapping("/admin/coupons/assign-birthday-coupons")
    BulkAssignResponseDTO assignBirthdayCoupons();

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


    @GetMapping("/admin/books/search/{keyword}")
    BookPageDTO getBooksByKeyword(@PathVariable("keyword") String keyword, Pageable pageable);

    @GetMapping("/admin/books/isbn/{isbn}")
    BookAladinDTO getAladinApiBookByIsbn(@PathVariable("isbn") String isbn);

    @DeleteMapping("/admin/books/{book-id}")
    void deleteBook(@PathVariable(name = "book-id") Long bookId);

    @PutMapping(value = "/admin/books", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    void updateBook(@RequestPart BookUpdateDTO bookDTO);

    @PostMapping(value = "/admin/books",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    void createBook(@RequestPart BookCreateDTO bookCreatorDTOs);

    @GetMapping("/admin/books/{id}")
    BookDTO getBookById(@PathVariable Long id);


    @PostMapping("/admin/books/category")
    void createCategory(@RequestBody List<CategoryDTO> categoryDTO);

    @GetMapping("/admin/books/categories")
    PageCategoryDTO getCategoryList(@RequestParam int level, Pageable pageable);

    @DeleteMapping("/admin/books/category")
    void deleteCategory(@RequestParam Long id);

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



    @GetMapping("/books/category-search")
    BookPageDetailResponseDTO getCategorySearch(@RequestParam long id, Pageable pageable);

    @GetMapping("/admin/books/categories/tree")
    List<CategoryResponseDTO> getCategoriesTree();

    @GetMapping("/admin/books/categories-level-one")
    CategoryLevelDTO getCategoryLevelList();


    @GetMapping("/admin/tags")
    Page<TagResponseDto> getAllTags();

    @GetMapping("/admin/book-tags/{bookId}")
    List<BookTagResponseDTO> getTagsByBookId(@PathVariable Long bookId);

    @PostMapping("/books/order-details")
    List<BookOrderDetailResponse> getBookOrderDetail(@RequestBody List<BookOrderRequestDTO> requestDTOS);

    @GetMapping("/api/coupons/available")
    List<AvailableCouponResponseDTO> getAvailableCouponByBookId(
            @RequestHeader("X-USER") Long userId,
            @RequestParam Long bookId,
            @RequestParam Long amount);
}
