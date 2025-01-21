package com.tripleseven.frontapi.dto.book;


import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookDetailResponseDTO {

    private Long id;

    private String title;

    private String publisher;

    private int regularPrice;

    private int salePrice;

    private String coverUrl;

    private List<String> creator;

    private LocalDate publishDate;
}
