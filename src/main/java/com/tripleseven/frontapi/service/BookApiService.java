package com.tripleseven.frontapi.service;

import com.tripleseven.frontapi.client.BookFeignClient;
import com.tripleseven.frontapi.dto.BookDetailResponseDTO;
import com.tripleseven.frontapi.dto.BookSearchResponseDTO;
import java.util.List;

import com.tripleseven.frontapi.dto.ReviewResponseDTO;
import com.tripleseven.frontapi.dto.SearchBookDetailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@RequiredArgsConstructor
public class BookApiService {
    private final BookFeignClient bookFeignClient;

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
}