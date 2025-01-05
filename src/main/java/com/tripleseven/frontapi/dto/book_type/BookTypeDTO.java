package com.tripleseven.frontapi.dto.book_type;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BookTypeDTO {

    private Long id;
    private int ranks;
    @JsonProperty("type")
    private String type;
    private Long bookId;

    public BookTypeDTO(Long id, int ranks, String type) {
        this.id = id;
        this.ranks = ranks;
        this.type = type;
    }
}
