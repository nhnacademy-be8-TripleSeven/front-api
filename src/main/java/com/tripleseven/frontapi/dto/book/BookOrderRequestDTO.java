package com.tripleseven.frontapi.dto.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookOrderRequestDTO {
    private long bookId;
    private int quantity;
}
