package com.tripleseven.frontapi.dto.order;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class OrderPayDetailDTO {
    List<OrderInfoDTO> orderInfos;
    OrderGroupInfoDTO orderGroupInfoDTO;
    DeliveryInfoDTO deliveryInfo;
    OrderPayInfoDTO orderPayInfoDTO;
}
