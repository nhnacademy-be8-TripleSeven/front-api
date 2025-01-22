package com.tripleseven.frontapi.dto.order;

import com.tripleseven.frontapi.dto.book.BookDetailViewDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCompleteDTO {
    private String title;
    private String image;
    private int quantity;
    private long price;
    private LocalDate deliveryDate;
    private long discountedPrice;

    public OrderCompleteDTO(OrderDetailResponseDTO orderDetailResponseDTO, BookDetailViewDTO bookDetailViewDTO, DeliveryInfoResponseDTO deliveryInfoResponseDTO) {
        this.title = bookDetailViewDTO.getTitle();
        this.image = bookDetailViewDTO.getCoverUrl();
        this.quantity = orderDetailResponseDTO.getQuantity();
        this.price = bookDetailViewDTO.getRegularPrice();
        this.deliveryDate = deliveryInfoResponseDTO.getArrivedAt();
        this.discountedPrice = bookDetailViewDTO.getSalePrice();

    }

}
