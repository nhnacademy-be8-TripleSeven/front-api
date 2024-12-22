package com.tripleseven.frontapi.service;

import com.tripleseven.frontapi.client.BookFeignClient;
import com.tripleseven.frontapi.dto.BookDetailResponseDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookApiService {
    private final BookFeignClient bookFeignClient;

    public List<BookDetailResponseDTO> fetchMonthlyBooks() {
        return bookFeignClient.getMonthlyBooks();
    }
}