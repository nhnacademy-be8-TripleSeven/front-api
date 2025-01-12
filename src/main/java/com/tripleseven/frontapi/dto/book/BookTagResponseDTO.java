package com.tripleseven.frontapi.dto.book;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookTagResponseDTO {

    private Long bookId;
    private Long tagId;
    private String tagName;
}
