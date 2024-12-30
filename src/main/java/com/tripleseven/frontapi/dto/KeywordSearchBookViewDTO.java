package com.tripleseven.frontapi.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class KeywordSearchBookViewDTO {

    private String keyword;
    private String path;
    private List<BookSearchResponseDTO> contents;
    private int page;
    private int size;
    private String sortField;
    private String sortDir;
}
