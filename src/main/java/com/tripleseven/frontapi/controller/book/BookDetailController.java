package com.tripleseven.frontapi.controller.book;

import com.tripleseven.frontapi.dto.review.ReviewRequestDTO;
import com.tripleseven.frontapi.dto.review.ReviewResponseDTO;
import com.tripleseven.frontapi.dto.SearchBookDetailDTO;
import com.tripleseven.frontapi.service.BookApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookDetailController {

    private final BookApiService bookApiService;

    @GetMapping("/books/{bookId}")
    public String bookDetail(
            @PathVariable Long bookId,
            @RequestHeader(value = "X-User", required = false) Long userId,
            Model model) {
        SearchBookDetailDTO book = bookApiService.getBookDetail(bookId);
        List<ReviewResponseDTO> reviews = bookApiService.getAllReviewsByBookId(bookId);

        //Long tempUserId = 100L;
        int sum = 0;
        double avg = 0.0;
        String resAvg = "";
        if (!reviews.isEmpty()) {
            for (ReviewResponseDTO review : reviews) {
                sum += review.getRating();
            }
            avg = (double) sum / reviews.size();
            resAvg = String.format("%.1f", avg);
        }
        int avgInt = (int) Math.round(avg);
        String stars = "★".repeat(avgInt) + "☆".repeat(5 - avgInt);

        boolean isLoggedIn = userId != null;
        ReviewResponseDTO userReview = null;
        String userReviewStars = null;

        if (isLoggedIn) {
            userReview = bookApiService.getUserReviewForBook(bookId, userId);
            if (userReview != null) {
                int rating = userReview.getRating();
                userReviewStars = "★".repeat(rating) + "☆".repeat(5 - rating);
            }
        }

        book.setId(bookId);
        model.addAttribute("book", book);
        model.addAttribute("formattedPublishedDate",
                book.getPublishedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        model.addAttribute("totalReviews", reviews.size());
        model.addAttribute("avgRating", resAvg);
        model.addAttribute("ratingStars", stars);
        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("userReview", userReview);
        model.addAttribute("userReviewStars", userReviewStars);

        return "order-detail"; // HTML 페이지 렌더링
    }
    // 리뷰 페이징 처리
    @ResponseBody
    @GetMapping("/api/reviews/{bookId}")
    public Page<ReviewResponseDTO> getPagedReviews(
            @PathVariable Long bookId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bookApiService.getPagedReviewsByBookId(bookId, pageable);
    }

    // 도서 상세 페이지에서 리뷰 등록하는 메소드
    //@RequestHeader("X-User")
    @PostMapping("/api/reviews")
    public RedirectView submitReview(@ModelAttribute ReviewRequestDTO reviewRequestDTO,
                                     @RequestHeader("X-User") Long userId) {
        // 유저가 해당 도서를 구매했는지 확인
//        boolean hasPurchased = bookApiService.checkUserPurchase(userId, reviewRequestDTO.getBookId());
//        if (!hasPurchased) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
        //Long randUserId = (long) (Math.random() * 1000) + 1;
        bookApiService.submitReview(userId, reviewRequestDTO);
        return new RedirectView("/books/" + reviewRequestDTO.getBookId());
    }
}
