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
import com.tripleseven.frontapi.dto.category.CategoryLevelDTO;
import com.tripleseven.frontapi.dto.category.CategoryResponseDTO;
import com.tripleseven.frontapi.dto.category.CategorySearchDTO;
import com.tripleseven.frontapi.dto.category.PageCategoryDTO;
import java.util.List;

import com.tripleseven.frontapi.dto.review.ReviewRequestDTO;
import com.tripleseven.frontapi.dto.review.ReviewResponseDTO;
import com.tripleseven.frontapi.dto.book.BookDetailViewDTO;
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

    public boolean checkUserPurchase(Long userId, Long bookId) {
        return orderFeignClient.checkUserPurchase(userId, bookId);
    }
  
    public BookPageDetailResponseDTO getTypeBookSearch(String type, Pageable pageable) {


        BookPageDetailResponseDTO typeSearchBooks = bookFeignClient.getTypeSearchBooks(type, pageable);

        return typeSearchBooks;
    }

    public BookPageDetailResponseDTO getCategorySearchBook(List<String> categories, String keyword, Pageable pageable) {


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

    public PageCategoryDTO getCategroyByLevel(int level, Pageable pageable) {
        return bookFeignClient.getCategoryList(level, pageable);
    }

    public void createCategory(List<CategoryDTO> categoryDTOS){
        bookFeignClient.createCategory(categoryDTOS);
    }

    public void deleteCategory(Long categoryId) {
        bookFeignClient.deleteCategory(categoryId);
    }

    public List<CategoryResponseDTO> getAllCategories() {
        return bookFeignClient.getCategoriesTree();
    }

    public BookPageDetailResponseDTO getCategorySearch(long id, Pageable pageable) {
        return bookFeignClient.getCategorySearch(id, pageable);
    }

    public CategoryLevelDTO getCategoryLevel(){
        return bookFeignClient.getCategoryLevelList();
    }

}