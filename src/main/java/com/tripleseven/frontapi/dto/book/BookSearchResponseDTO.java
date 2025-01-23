package com.tripleseven.frontapi.dto.book;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BookSearchResponseDTO {

    private String id; // Elasticsearch의 _id

    private String title; // 책 제목

    private String description; // 책 설명

    private String isbn13; // ISBN-13 번호

    private LocalDate publishDate; // 발행일

    private int regularPrice; // 정가

    private int salePrice; // 판매가

    private int stock; // 재고

    private int page; // 페이지 수

    private String coverUrl; // 책 커버 URL

    private String publisherName; // 출판사 이름

    private String bookCreators; // 저자, 편집자 등

    private List<String> categories; // 카테고리
}
