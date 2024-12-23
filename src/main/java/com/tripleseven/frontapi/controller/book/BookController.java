package com.tripleseven.frontapi.controller.book;

import com.tripleseven.frontapi.dto.BookDetailResponseDTO;
import com.tripleseven.frontapi.service.BookApiService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookController {
    private final BookApiService bookApiService;

    @GetMapping("/books/monthly")
    public ResponseEntity<List<BookDetailResponseDTO>> getMonthlyBooks() {
        List<BookDetailResponseDTO> dto = bookApiService.fetchMonthlyBooks();

        return ResponseEntity.ok(dto);
    }
}