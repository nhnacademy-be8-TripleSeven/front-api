package com.tripleseven.frontapi.controller.book;

import com.tripleseven.frontapi.dto.review.ReviewResponseDTO;
import com.tripleseven.frontapi.dto.book.BookDetailViewDTO;
import com.tripleseven.frontapi.service.BookService;
import com.tripleseven.frontapi.service.LikesService;
import com.tripleseven.frontapi.service.ObjectStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookDetailController {

    private final BookService bookApiService;
    private final ObjectStorageService objectStorageService;

    private final LikesService likesService;


    @GetMapping("/frontend/books/{bookId}")
    public String bookDetail(
            @PathVariable Long bookId,
            @RequestHeader(value = "X-USER", required = false) Long userId,
            Model model) {
        BookDetailViewDTO book = bookApiService.getBookDetail(bookId);
        List<ReviewResponseDTO> reviews = bookApiService.getAllReviewsByBookId(bookId);
//        boolean isLiked = false;
//        if (userId != null) {
//            isLiked = likesService.isLiked(userId, bookId);
//        }
        boolean isPurchased = false;
        if (userId != null) {
            isPurchased = bookApiService.checkUserPurchase(bookId, userId);
        }
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
        model.addAttribute("isPurchased", isPurchased);
        model.addAttribute("userId", userId);
        //model.addAttribute("isLiked", isLiked);
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
    @GetMapping("/frontend/reviews/{bookId}")
    public Page<ReviewResponseDTO> getPagedReviews(
            @PathVariable Long bookId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bookApiService.getPagedReviewsByBookId(bookId, pageable);
    }
}
