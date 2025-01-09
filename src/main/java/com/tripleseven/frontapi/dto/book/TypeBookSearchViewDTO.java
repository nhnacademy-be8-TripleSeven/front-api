package com.tripleseven.frontapi.dto.book;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

@AllArgsConstructor
@Getter
public class TypeBookSearchViewDTO {

    private String search;
    private String path;
    private Page<BookDetailResponseDTO> content;
    private int startPage;
    private int endPage;
}
