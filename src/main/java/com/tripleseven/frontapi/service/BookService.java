package com.tripleseven.frontapi.service;

import com.tripleseven.frontapi.client.BookFeignClient;
import com.tripleseven.frontapi.client.OrderFeignClient;
import com.tripleseven.frontapi.dto.book.BookAladinDTO;
import com.tripleseven.frontapi.dto.book.BookApiDTO;
import com.tripleseven.frontapi.dto.book.BookCreateDTO;
import com.tripleseven.frontapi.dto.book.BookDTO;
import com.tripleseven.frontapi.dto.book.BookDetailResponseDTO;
import com.tripleseven.frontapi.dto.book.BookPageDTO;
import com.tripleseven.frontapi.dto.book.BookPageResponseDTO;
import com.tripleseven.frontapi.dto.book.BookSearchResponseDTO;
import com.tripleseven.frontapi.dto.book.BookPageDetailResponseDTO;
import com.tripleseven.frontapi.dto.book.BookUpdateDTO;
import com.tripleseven.frontapi.dto.book_creator.BookCreatorDTO;
import com.tripleseven.frontapi.dto.category.CategoryDTO;
import java.util.List;

import com.tripleseven.frontapi.dto.review.ReviewRequestDTO;
import com.tripleseven.frontapi.dto.review.ReviewResponseDTO;
import com.tripleseven.frontapi.dto.book.BookDetailViewDTO;
import com.tripleseven.frontapi.dto.tag.TagResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

    public BookPageResponseDTO searchBooks(String term, Pageable pageable) {

        if(pageable.getSort() == null) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        }

        BookPageResponseDTO booksByTerm = bookFeignClient.getBooksByTerm(term, pageable);


        return booksByTerm;
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

    public boolean checkUserPurchase(Long bookId, Long userId) {
        return orderFeignClient.checkUserPurchase(bookId, userId);
    }
  
    public BookPageDetailResponseDTO getTypeBookSearch(String type, int page, int pageSize, String sortField, String sortDir) {

        Sort sort = Sort.unsorted();
        if(sortField != null) {
            Sort.Direction direction = Sort.Direction.fromString(sortDir);
            sort = Sort.by(direction, sortField);
        }
        Pageable pageable = PageRequest.of(page, pageSize, sort);

        BookPageDetailResponseDTO typeSearchBooks = bookFeignClient.getTypeSearchBooks(type, pageable);

        return typeSearchBooks;
    }

    public BookPageDetailResponseDTO getCategorySearchBook(List<String> categories, String keyword, int page, int pageSize, String sortField, String sortDir) {

        Sort sort = Sort.unsorted();
        Pageable pageable = null;
        if(sortField != null) {
            Sort.Direction direction = Sort.Direction.fromString(sortDir);
            sort = Sort.by(direction, sortField);
            pageable = PageRequest.of(page, pageSize, sort);
        }else {
            pageable = PageRequest.of(page, pageSize);
        }
        BookPageDetailResponseDTO searchBooks = bookFeignClient.getCategoriesSearchBooks(
            categories, keyword, pageable);

        return searchBooks;
    }


    public BookPageDTO getAdminBooksByKeyword(String keyword, Pageable pageable) {
        return bookFeignClient.getBooksByKeyword(keyword, pageable);
    }

    public BookAladinDTO getAladinApiBook(String isbn){
        return bookFeignClient.getAladinApiBookByIsbn(isbn);
    }

    public void deleteBook(Long bookId) {
        bookFeignClient.deleteBook(bookId);
    }

    public void updateBook(BookUpdateDTO bookDTO) {
        bookFeignClient.updateBook(bookDTO);

    }

    public void createBook(BookCreateDTO bookCreateDTO) {
       bookFeignClient.createBook(bookCreateDTO);
    }

    public BookDTO getBookById(Long bookId) {
        return bookFeignClient.getBookById(bookId);
    }

    public List<CategoryDTO> getCategroyByLevel(int level) {
        return bookFeignClient.getCategoryList(level);
    }

    public void createCategory(List<CategoryDTO> categoryDTOS){
        bookFeignClient.createCategory(categoryDTOS);
    }

    public void deleteCategory(Long categoryId) {
        bookFeignClient.deleteCategory(categoryId);
    }

    public List<TagResponseDto> getAllTags() {
        return bookFeignClient.getAllTags().getContent();
    }
}