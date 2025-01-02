package com.tripleseven.frontapi.service;

import com.tripleseven.frontapi.client.OrderFeignClient;
import com.tripleseven.frontapi.dto.FilterCriteriaDTO;
import com.tripleseven.frontapi.dto.order.OrderDetailDTO;
import com.tripleseven.frontapi.dto.order.OrderManageRequestDTO;
import com.tripleseven.frontapi.dto.order.OrderManageResponseDTO;
import com.tripleseven.frontapi.dto.order.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderFeignClient orderFeignClient;

    public Page<OrderManageResponseDTO> getOrderHistories(FilterCriteriaDTO filterCriteriaDTO, Pageable pageable) {
        Status status = filterCriteriaDTO.getStatus();
        if(status.equals(Status.ALL)){
            status = null;
        }
        OrderManageRequestDTO requestDTO = new OrderManageRequestDTO(
                filterCriteriaDTO.getStartDate(),
                filterCriteriaDTO.getEndDate(),
                status
        );
        return orderFeignClient.getOrderList(requestDTO, pageable);
    }

    public OrderDetailDTO getOrderHistory(Long orderId) {
        return orderFeignClient.getOrderDetails(orderId);
    }

    public int getPoints() {
        return orderFeignClient.getTotalPoint();
    }

}
