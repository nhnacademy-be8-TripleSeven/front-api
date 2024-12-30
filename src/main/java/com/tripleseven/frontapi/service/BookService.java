package com.tripleseven.frontapi.service;

import com.tripleseven.frontapi.client.BookFeignClient;
import com.tripleseven.frontapi.client.OrderFeignClient;
import com.tripleseven.frontapi.dto.BookDetailResponseDTO;
import com.tripleseven.frontapi.dto.BookPageResponseDTO;
import com.tripleseven.frontapi.dto.BookSearchResponseDTO;
import com.tripleseven.frontapi.dto.coupon.BookPageDetailResponseDTO;
import java.util.List;

import com.tripleseven.frontapi.dto.review.ReviewRequestDTO;
import com.tripleseven.frontapi.dto.review.ReviewResponseDTO;
import com.tripleseven.frontapi.dto.BookDetailViewDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {
    private final BookFeignClient bookFeignClient;
    private final OrderFeignClient orderFeignClient;

    public List<BookDetailResponseDTO> fetchMonthlyBooks() {
        return bookFeignClient.getMonthlyBooks();
    }

    public List<BookDetailResponseDTO> fetchBooksByType(String type) {
        List<BookDetailResponseDTO> booksByType = bookFeignClient.getBooksByType(type);
        return booksByType;
    }

    public List<BookSearchResponseDTO> searchBooks(String term, int page, int pageSize, String sortField, String sortDir) {
        Sort.Direction direction = Sort.Direction.fromString(sortDir);
        Sort sort = Sort.by(direction, sortField);
        Pageable pageable = PageRequest.of(page, pageSize, sort);

        BookPageResponseDTO booksByTerm = bookFeignClient.getBooksByTerm(term, page, pageSize, sortField + "," + sortDir);

        return booksByTerm.getContent();
    }




    public BookDetailViewDTO getBookDetail(Long bookId) {
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
  
    public List<BookDetailResponseDTO> getTypeBookSearch(String type, int page, int pageSize, String sortField, String sortDir) {
        Sort.Direction direction = Sort.Direction.fromString(sortDir);
        Sort sort = Sort.by(direction, sortField);
        Pageable pageable = PageRequest.of(page, pageSize, sort);

        BookPageDetailResponseDTO typeSearchBooks = bookFeignClient.getTypeSearchBooks(type, pageable);

        return typeSearchBooks.getContent();
    }

    public List<BookDetailResponseDTO> getCategorySearchBook(List<String> categories, String keyword, int page, int pageSize, String sortField, String sortDir) {
        Sort.Direction direction = Sort.Direction.fromString(sortDir);
        Sort sort = Sort.by(direction, sortField);
        Pageable pageable = PageRequest.of(page, pageSize, sort);

        BookPageDetailResponseDTO searchBooks = bookFeignClient.getCategoriesSearchBooks(
            categories, keyword, page, pageSize, sortField + "," + sortDir);

        log.info(searchBooks.toString());

        return searchBooks.getContent();
    }
}