package com.tripleseven.frontapi.dto.order;

import com.tripleseven.frontapi.dto.book.BookDetailViewDTO;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Getter
public class ProductDTO {
    private String title;        // 책 제목
    private String description;  // 책 설명
    private int price;           // 원가
    private int discountedPrice; // 할인된 가격
    private String coverUrl;     // 책 이미지 URL
    private int quantity;        // 주문 수량
    private String[] deliveryDate; // 예상 배송일 배열
    private double discountPercentage;
    private int totalPrice;
    private Long bookId;

    public void ofCreate(BookDetailViewDTO searchBookDetailDTO, int quantity) {
        this.title = searchBookDetailDTO.getTitle();
        this.description = searchBookDetailDTO.getDescription();
        this.price = searchBookDetailDTO.getRegularPrice();
        this.discountedPrice = searchBookDetailDTO.getSalePrice();
        this.coverUrl = searchBookDetailDTO.getCoverUrl();
        this.quantity = quantity;
        this.totalPrice = quantity * discountedPrice;
        this.bookId = 60L;

        // 배열 초기화 (5개의 예상 배송일)
        this.deliveryDate = new String[5];

        LocalDate localDate = LocalDate.now().plusDays(2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd(E)", Locale.KOREAN);
        for (int i = 0; i < 5; i++) {
            this.deliveryDate[i] = localDate.format(formatter); // 예: 01/04(목)
            localDate = localDate.plusDays(1);
        }

        this.discountPercentage = (double) (price - discountedPrice) / price * 100;
    }
}
