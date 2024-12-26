package com.tripleseven.frontapi.service;

import com.tripleseven.frontapi.client.BookFeignClient;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private final BookFeignClient bookFeignClient;

    public ReviewService(BookFeignClient bookFeignClient) {
        this.bookFeignClient = bookFeignClient;
    }

    
}
