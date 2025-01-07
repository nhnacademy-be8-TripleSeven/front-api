package com.tripleseven.frontapi.dto.book;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class KeywordSearchBookViewDTO {

    private String keyword;
    private String path;
    private List<BookSearchResponseDTO> content;
    private int page;
    private int size;
    private String sortField;
    private String sortDir;
    private long total;
    private int startPage;
    private int endPage;
}
