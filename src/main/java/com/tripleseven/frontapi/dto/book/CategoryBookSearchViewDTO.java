package com.tripleseven.frontapi.dto.book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

@AllArgsConstructor
@Getter
public class CategoryBookSearchViewDTO {

    private Long search;
    private String path;
    private Page<BookDetailResponseDTO> content;
    private int startPage;
    private int endPage;





}
