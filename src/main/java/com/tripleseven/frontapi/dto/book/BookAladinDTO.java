package com.tripleseven.frontapi.dto.book;

import com.tripleseven.frontapi.dto.book_creator.BookCreatorDTO;
import com.tripleseven.frontapi.dto.book_type.BookTypeDTO;
import com.tripleseven.frontapi.dto.category.CategoryDTO;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class BookAladinDTO {

    private Long id;
    private String title;
    private String isbn;
    private List<CategoryDTO> categories;
    private List<BookTypeDTO> bookTypes;
    private List<BookCreatorDTO> authors;
    private List<String> tags;
    private LocalDate publishedDate;
    private String description;
    private int regularPrice;
    private int salePrice;
    private String index;
    private List<String> coverImage;
    private List<String> detailImage;
    private int stock;
    private int page;
    private String publisherName;

    public BookAladinDTO(String title, String isbn, List<CategoryDTO> categories,
        List<BookTypeDTO> bookTypes, List<BookCreatorDTO> authors, List<String> tags,
        LocalDate publishedDate, String description, int regularPrice, int salePrice, String index,
        List<String> coverImage, List<String> detailImage, int stock, int page,
        String publisherName) {
        this.title = title;
        this.isbn = isbn;
        this.categories = categories;
        this.bookTypes = bookTypes;
        this.authors = authors;
        this.tags = tags;
        this.publishedDate = publishedDate;
        this.description = description;
        this.regularPrice = regularPrice;
        this.salePrice = salePrice;
        this.index = index;
        this.coverImage = coverImage;
        this.detailImage = detailImage;
        this.stock = stock;
        this.page = page;
        this.publisherName = publisherName;
    }
}
