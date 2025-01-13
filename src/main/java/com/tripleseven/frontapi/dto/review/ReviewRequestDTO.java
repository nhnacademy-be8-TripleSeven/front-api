package com.tripleseven.frontapi.dto.review;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequestDTO {

    private Long bookId;
    private int rating;
    private String text;

    public ReviewRequestDTO() {}

    public ReviewRequestDTO(Long bookId, int rating, String text) {
        this.bookId = bookId;
        this.rating = rating;
        this.text = text;
    }
}
