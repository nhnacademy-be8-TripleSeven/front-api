package com.tripleseven.frontapi.dto.review;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReviewResponseDTO {

    private String text;
    private int rating;
    private LocalDateTime createdAt;

    public ReviewResponseDTO() {}

    public ReviewResponseDTO(String text, int rating, LocalDateTime createdAt) {
        this.text = text;
        this.rating = rating;
        this.createdAt = createdAt;
    }
}
