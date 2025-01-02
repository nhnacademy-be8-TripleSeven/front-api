package com.tripleseven.frontapi.dto.order;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderDetailDTO {
    List<OrderInfoDTO> orderInfos;
    DeliveryInfoDTO deliveryInfo;
    PayInfoDTO payInfo;
}
