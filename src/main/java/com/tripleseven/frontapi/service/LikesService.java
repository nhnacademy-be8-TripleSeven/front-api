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
}
