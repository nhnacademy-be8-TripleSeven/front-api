package com.tripleseven.frontapi.dto.order;

import lombok.Getter;

import java.util.List;

@Getter
public class TotalOrderCompleteDTO {
    private List<OrderCompleteDTO> orders;
    private long totalPrice;
    private long totalDiscountedPrice;
    private long wrappingPrice;

    public TotalOrderCompleteDTO(List<OrderCompleteDTO> orders) {
        this.orders = orders;
        this.totalPrice = orders.stream().mapToLong(OrderCompleteDTO::getPrice).sum();
        this.totalDiscountedPrice = orders.stream().mapToLong(OrderCompleteDTO::getDiscountedPrice).sum();
        this.wrappingPrice = 0;
    }
    public TotalOrderCompleteDTO(List<OrderCompleteDTO> orders, WrappingResponseDTO wrappingResponseDTO) {
        this.orders = orders;
        this.totalPrice = orders.stream().mapToLong(OrderCompleteDTO::getPrice).sum();
        this.totalDiscountedPrice = orders.stream().mapToLong(OrderCompleteDTO::getDiscountedPrice).sum();
        this.wrappingPrice = wrappingResponseDTO.getPrice();
    }
}
