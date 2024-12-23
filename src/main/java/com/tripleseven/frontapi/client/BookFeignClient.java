package com.tripleseven.frontapi.client;

import com.tripleseven.frontapi.dto.BookDetailResponseDTO;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "gateway", contextId = "book")
public interface BookFeignClient {
    @GetMapping("/api/books/monthly")
    List<BookDetailResponseDTO> getMonthlyBooks();
}
