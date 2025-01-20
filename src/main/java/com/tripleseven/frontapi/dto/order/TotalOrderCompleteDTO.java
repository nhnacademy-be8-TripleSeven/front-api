package com.tripleseven.frontapi.dto.order;

import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class TotalOrderCompleteDTO {
    private List<OrderCompleteDTO> orders;
    private long totalPrice;
    private long totalDiscountedPrice;
    private long wrappingPrice;
    private LocalDate arrivedAt;

    public TotalOrderCompleteDTO(List<OrderCompleteDTO> orders, DeliveryInfoResponseDTO deliveryInfoResponseDTO) {
        this.orders = orders;
        this.totalPrice = orders.stream().mapToLong(order -> order.getPrice() * order.getQuantity()).sum();
        this.totalDiscountedPrice = orders.stream().mapToLong(order -> order.getDiscountedPrice() * order.getQuantity()).sum();
        this.wrappingPrice = 0;
        this.arrivedAt = deliveryInfoResponseDTO.getArrivedAt();
    }

    public TotalOrderCompleteDTO(List<OrderCompleteDTO> orders, WrappingResponseDTO wrappingResponseDTO, DeliveryInfoResponseDTO deliveryInfoResponseDTO) {
        this.orders = orders;
        this.totalPrice = orders.stream().mapToLong(order -> order.getPrice() * order.getQuantity()).sum();
        this.totalDiscountedPrice = orders.stream().mapToLong(order -> order.getDiscountedPrice() * order.getQuantity()).sum();
        this.wrappingPrice = wrappingResponseDTO.getPrice();
        this.arrivedAt = deliveryInfoResponseDTO.getArrivedAt();
    }
}
