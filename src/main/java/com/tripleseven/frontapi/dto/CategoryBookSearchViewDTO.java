package com.tripleseven.frontapi.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class CategoryBookSearchViewDTO {

    private String keyword;
    private List<String> categories;
    private String path;
    private List<BookDetailResponseDTO> contents;
    private int page;
    private int size;
    private String sortField;
    private String sortDir;



    public CategoryBookSearchViewDTO(String keyword, List<String> categories, List<BookDetailResponseDTO> contents, String path, int page, int size, String sortField, String sortDir) {
        this.keyword = keyword;
        this.categories = categories;
        this.path = path;
        this.contents = contents;
        this.page = page;
        this.size = size;
        this.sortField = sortField;
        this.sortDir = sortDir;
    }



}
