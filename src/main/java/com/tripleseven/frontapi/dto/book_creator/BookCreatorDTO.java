package com.tripleseven.frontapi.dto.book_creator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookCreatorDTO {

    private Long id;
    private String name;
    private String role;
}
