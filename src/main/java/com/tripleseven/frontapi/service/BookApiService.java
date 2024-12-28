package com.tripleseven.frontapi.service;

import com.tripleseven.frontapi.client.BookFeignClient;
import com.tripleseven.frontapi.dto.BookDetailResponseDTO;
import com.tripleseven.frontapi.dto.BookPageResponseDTO;
import com.tripleseven.frontapi.dto.BookSearchResponseDTO;
import java.util.List;

import com.tripleseven.frontapi.dto.ReviewResponseDTO;
import com.tripleseven.frontapi.dto.SearchBookDetailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Page<BookSearchResponseDTO> searchBooks(String term, int page, int pageSize) {

        BookPageResponseDTO booksByTerm = bookFeignClient.getBooksByTerm(term, page, pageSize);

        return new PageImpl<>(
            booksByTerm.getContent(),
            PageRequest.of(booksByTerm.getNumber(),booksByTerm.getSize()),
            booksByTerm.getTotalElements());
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

    public Page<BookSearchResponseDTO> getTypeBookSearch(String type, int page, int pageSize) {
        BookPageResponseDTO typeSearchBooks = bookFeignClient.getTypeSearchBooks(type, page, pageSize);

        return new PageImpl<>(
            typeSearchBooks.getContent(),
            PageRequest.of(typeSearchBooks.getNumber(), typeSearchBooks.getSize()),
            typeSearchBooks.getTotalElements()
        );
    }
}