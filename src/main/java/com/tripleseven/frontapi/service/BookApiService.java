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

    public List<BookDetailResponseDTO> fetchBooksByType(String type) {
        List<BookDetailResponseDTO> booksByType = bookFeignClient.getBooksByType(type);
        BookDetailResponseDTO bookDetailResponseDTO = booksByType.get(0);
        BookDetailResponseDTO bookDetailResponseDTO1 = booksByType.get(1);


        return bookFeignClient.getBooksByType(type);
    }
}