package com.tripleseven.frontapi.dto.book;


import com.tripleseven.frontapi.dto.book_creator.BookCreatorDTO;
import com.tripleseven.frontapi.dto.book_type.BookTypeDTO;
import com.tripleseven.frontapi.dto.category.CategoryDTO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BookApiDTO {

    private String title;
    private String isbn;
    private List<CategoryDTO> categories;
    private List<BookTypeDTO> bookTypes;
    private List<BookCreatorDTO> authors;
    private LocalDate publishedDate;
    private String description;
    private int regularPrice;
    private int salePrice;
    private List<String> coverImage;
    private int stock;
    private int page;

    public BookApiDTO(String title, String isbn, LocalDate publishedDate, String description,
        int regularPrice, int salePrice, List<String> coverImage, int stock, int page) {
        this.title = title;
        this.isbn = isbn;
        this.publishedDate = publishedDate;
        this.description = description;
        this.regularPrice = regularPrice;
        this.salePrice = salePrice;
        this.coverImage = coverImage;
        this.stock = stock;
        this.page = page;
    }




}
