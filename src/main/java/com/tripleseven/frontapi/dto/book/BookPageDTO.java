package com.tripleseven.frontapi.dto.book;

import java.util.List;
import lombok.Getter;

@Getter
public class BookPageDTO {
    private List<BookDTO> content;
    private int number; // 현재 페이지 번호
    private int size; // 페이지 크기
    private int totalPages; // 전체 페이지 수
    private long totalElements; // 전체 항목 수
}
