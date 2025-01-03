package com.tripleseven.frontapi.service;

import com.tripleseven.frontapi.client.BookFeignClient;
import com.tripleseven.frontapi.dto.likes.LikesResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikesService {

    private BookFeignClient bookFeignClient;

    public LikesService(BookFeignClient bookFeignClient) {
        this.bookFeignClient = bookFeignClient;
    }

    public List<LikesResponseDTO> getAllLikesByUserId(Long userId, int page, int size) {
        return bookFeignClient.getAllLikesByUserId(userId, page, size);
    }

    public List<LikesResponseDTO> searchLikes(Long userId, String keyword, int page, int size) {
        return bookFeignClient.searchLikesByUserIdAndKeyword(userId, keyword, page, size);
    }

    public boolean isLiked(Long userId, Long bookId) {
        return bookFeignClient.isLiked(userId, bookId);
    }

    public void addLikes(Long bookId, Long userId) {
        bookFeignClient.addLikes(bookId, userId);
    }

    public void deleteLikes(Long bookId, Long userId) {
        bookFeignClient.deleteLikes(bookId, userId);
    }
}
