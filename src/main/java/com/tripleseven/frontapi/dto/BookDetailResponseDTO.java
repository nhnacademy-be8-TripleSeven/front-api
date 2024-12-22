package com.tripleseven.frontapi.dto;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class BookDetailResponseDTO {

    private long id;

    private String title;

    private String publisher;

    private int regularPrice;

    private int salePrice;

    private String coverUrl;

    private List<String> creator;
}
