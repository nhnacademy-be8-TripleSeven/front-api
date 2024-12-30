package com.tripleseven.frontapi.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TypeBookSearchViewDTO {

    private String type;
    private String path;
    private List<BookDetailResponseDTO> contents;
    private int page;
    private int size;
    private String sortField;
    private String sortDir;
}
