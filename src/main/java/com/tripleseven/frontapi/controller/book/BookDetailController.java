package com.tripleseven.frontapi.controller.book;

import com.tripleseven.frontapi.dto.review.ReviewRequestDTO;
import com.tripleseven.frontapi.dto.review.ReviewResponseDTO;
import com.tripleseven.frontapi.dto.book.BookDetailViewDTO;
import com.tripleseven.frontapi.service.BookService;
import com.tripleseven.frontapi.service.LikesService;
import com.tripleseven.frontapi.service.ObjectStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;
import java.io.IOException;
import java.io.InputStream;
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

    // 도서 상세 페이지에서 리뷰 등록하는 메소드
    //@RequestHeader("X-User") Long userId
    @PostMapping("/api/frontend/reviews")
    public RedirectView submitReview(@RequestParam(value = "file", required = false) MultipartFile file,
                                     @RequestHeader("X-USER") Long userId,
                                     @RequestParam("rating") int rating,
                                     @RequestParam("text") String text,
                                     @RequestParam("bookId") Long bookId) {
        // 유저가 해당 도서를 구매했는지 확인

        boolean hasPurchased = bookApiService.checkUserPurchase(userId, bookId);
        if (!hasPurchased) {
            throw new IllegalArgumentException("You are not purchased this book");
        }

//        boolean hasPurchased = bookApiService.checkUserPurchase(userId, reviewRequestDTO.getBookId());
//        if (!hasPurchased) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }

        String imageUrl = null;
        objectStorageService.generateAuthToken("https://api-identity.infrastructure.cloud.toast.com/v2.0/tokens",
                "c20e3b10d61749a2a52346ed0261d79e",
                "rlgus4531@naver.com",
                "team3");
        if (file != null && !file.isEmpty()) {
            try (InputStream inputStream = file.getInputStream()) {
                objectStorageService.uploadObject("triple-seven", file.getOriginalFilename(), inputStream);
                imageUrl = objectStorageService.getStorageUrl() + "/triple-seven/" + file.getOriginalFilename();
            } catch (IOException e) {
                throw new RuntimeException("image upload failed", e);
            }
        }
        ReviewRequestDTO reviewRequestDTO = new ReviewRequestDTO(bookId, rating, text, imageUrl);
        bookApiService.submitReview(userId, reviewRequestDTO);
        return new RedirectView("/frontend/books/" + reviewRequestDTO.getBookId());
    }
}
