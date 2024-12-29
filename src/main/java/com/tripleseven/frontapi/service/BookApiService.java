package com.tripleseven.frontapi.service;

import com.tripleseven.frontapi.client.BookFeignClient;
import com.tripleseven.frontapi.client.OrderFeignClient;
import com.tripleseven.frontapi.dto.BookDetailResponseDTO;
import com.tripleseven.frontapi.dto.BookSearchResponseDTO;
import java.util.List;

import com.tripleseven.frontapi.dto.review.ReviewRequestDTO;
import com.tripleseven.frontapi.dto.review.ReviewResponseDTO;
import com.tripleseven.frontapi.dto.SearchBookDetailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookApiService {
    private final BookFeignClient bookFeignClient;
    private final OrderFeignClient orderFeignClient;

    public List<BookDetailResponseDTO> fetchMonthlyBooks() {
        return bookFeignClient.getMonthlyBooks();
    }

    public List<BookDetailResponseDTO> fetchBooksByType(String type) {
        List<BookDetailResponseDTO> booksByType = bookFeignClient.getBooksByType(type);
        return booksByType;
    }

    public List<BookSearchResponseDTO> searchBooks(String term) {
        List<BookSearchResponseDTO> books = bookFeignClient.getBooksByTerm(term);
        BookSearchResponseDTO bookSearchResponseDTO = books.get(0);

        return books;
    }

    public SearchBookDetailDTO getBookDetail(Long bookId) {
        return bookFeignClient.getBookDetail(bookId);
    }

    public Page<ReviewResponseDTO> getPagedReviewsByBookId(Long bookId, Pageable pageable) {
        return bookFeignClient.getPagedReviewsByBookId(bookId, pageable.getPageNumber(), pageable.getPageSize());
    }

    public List<ReviewResponseDTO> getAllReviewsByBookId(Long bookId) {
        return bookFeignClient.getAllReviewByBookId(bookId);
    }

    public ReviewResponseDTO getUserReviewForBook(Long bookId, Long userId) {
        try {
            return bookFeignClient.getUserReviewForBook(bookId, userId);
        } catch (Exception e) {
            return null;
        }
    }

    public void submitReview(Long userId, ReviewRequestDTO reviewRequestDTO) {
        bookFeignClient.addReview(userId, reviewRequestDTO);
    }

    public boolean checkUserPurchase(Long userId, Long bookId) {
        return orderFeignClient.checkUserPurchase(userId, bookId);
    }

    
}