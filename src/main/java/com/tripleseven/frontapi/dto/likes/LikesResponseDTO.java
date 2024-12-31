package com.tripleseven.frontapi.dto.likes;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class LikesResponseDTO {

    private Long bookId;
    private String bookTitle;
    private LocalDateTime createdAt;

    public LikesResponseDTO() {}

    public LikesResponseDTO(Long bookId, String bookTitle, LocalDateTime createdAt) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.createdAt = createdAt;
    }
}
