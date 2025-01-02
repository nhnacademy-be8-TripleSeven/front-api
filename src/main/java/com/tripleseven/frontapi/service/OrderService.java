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

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderFeignClient orderFeignClient;

    public Page<OrderManageResponseDTO> getOrderHistories(FilterCriteriaDTO filterCriteriaDTO, Long userId, Pageable pageable) {
        Status status = filterCriteriaDTO.getStatus();
        if (status.equals(Status.ALL)) {
            status = null;
        }
        OrderManageRequestDTO requestDTO = new OrderManageRequestDTO(
                filterCriteriaDTO.getStartDate(),
                filterCriteriaDTO.getEndDate(),
                status
        );
        return orderFeignClient.getOrderList(requestDTO, userId, pageable);
    }

    public OrderDetailDTO getOrderHistory(Long userId, Long orderId) {
        return orderFeignClient.getOrderDetails(userId, orderId);
    }

    public int getPoints(Long userId) {
        return orderFeignClient.getTotalPoint(userId);
    }

}
