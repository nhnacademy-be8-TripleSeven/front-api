package com.tripleseven.frontapi.dto.book;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

@Getter
@AllArgsConstructor
public class BookPageDetailResponseDTO {
    private List<BookDetailResponseDTO> content;
    private int number; // 현재 페이지 번호
    private int size; // 페이지 크기
    private int totalPages; // 전체 페이지 수
    private long totalElements; // 전체 항목 수
    private int page;
    private String sortField;
    private String sortDir;
    private long total;
    private String path;


    public void addPath(String path) {
        this.path = path;
    }
}
