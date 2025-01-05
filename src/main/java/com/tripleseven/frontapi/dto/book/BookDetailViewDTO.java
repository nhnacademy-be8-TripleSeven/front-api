package com.tripleseven.frontapi.dto.book;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class BookDetailViewDTO {

    private Long id;
    private String title;
    private String description;
    private LocalDate publishedDate;
    private int regularPrice;
    private int salePrice;
    private String isbn13;
    private int stock;
    private int page;
    private String coverUrl;
    private String publisher;
    //private String ImageDetailUrl;
    private List<BookCreatorDetailDTO> bookCreators;
    //private List<BookIndex> bookIndices;
    private StringBuilder categories;
    private String bookIndex;
    private StringBuilder tags;
    private StringBuilder bookTypes;
    private List<String> detailImages;
    public BookDetailViewDTO() {}

    public BookDetailViewDTO(String title, String description, LocalDate localDate,
                            int regularPrice, int salePrice, String isbn13, int stock,
                            int page, String coverUrl, String publisher, List<BookCreatorDetailDTO> bookCreators,
                            StringBuilder categories, String bookIndex, StringBuilder tags, StringBuilder bookTypes
    , List<String> detailImages) {
        this.title = title;
        this.description = description;
        this.publishedDate = localDate;
        this.regularPrice = regularPrice;
        this.salePrice = salePrice;
        this.isbn13 = isbn13;
        this.stock = stock;
        this.page = page;
        this.coverUrl = coverUrl;
        this.publisher = publisher;
        this.bookCreators = bookCreators;
        this.categories = categories;
        this.bookIndex = bookIndex;
        this.tags = tags;
        this.bookTypes = bookTypes;
        this.detailImages = detailImages;
    }
}
