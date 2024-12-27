package com.tripleseven.frontapi.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@RequiredArgsConstructor
public class BookSearchResponseDTO {

    private int id;
    private String title;
    private String isbn13;
    private String description;
    private LocalDate publishDate;
    private int regularPrice;
    private int salePrice;
    private int stock;
    private int page;
    private int bestSellerRank;
    private int clickCount;
    private int searchCount;
    private int cartCount;
    private String coverUrl;
    private String publisherName;
    private String bookcreator;
    private List<String> categories;
}
