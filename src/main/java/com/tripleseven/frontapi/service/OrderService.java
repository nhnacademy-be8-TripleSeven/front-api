package com.tripleseven.frontapi.service;

import com.tripleseven.frontapi.client.OrderFeignClient;
import com.tripleseven.frontapi.dto.FilterCriteriaDTO;
import com.tripleseven.frontapi.dto.order.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderFeignClient orderFeignClient;

    public Page<OrderManageResponseDTO> getOrderHistories(FilterCriteriaDTO filterCriteriaDTO, Long userId, Pageable pageable) {
        OrderStatus orderStatus = filterCriteriaDTO.getOrderStatus();
        if (orderStatus.equals(OrderStatus.ALL)) {
            orderStatus = null;
        }
        OrderManageRequestDTO requestDTO = new OrderManageRequestDTO(
                filterCriteriaDTO.getStartDate(),
                filterCriteriaDTO.getEndDate(),
                orderStatus
        );
        return orderFeignClient.getOrderList(requestDTO, userId, pageable);
    }

    public OrderPayDetailDTO getOrderHistory(Long userId, Long orderId) {
        return orderFeignClient.getOrderDetails(userId, orderId);
    }

    public int getPoints(Long userId) {
        return orderFeignClient.getTotalPoint(userId);
    }

    public void updateOrderHistories(Long userId, List<Long> ids, OrderStatus orderStatus){
        OrderDetailUpdateRequestDTO orderDetailUpdateRequest = new OrderDetailUpdateRequestDTO(ids, orderStatus);
        orderFeignClient.updateOrderDetails(userId, orderDetailUpdateRequest);
    }

    public Page<OrderManageResponseDTO> getAdminOrderHistories(FilterCriteriaDTO filterCriteriaDTO, Pageable pageable) {
        OrderStatus orderStatus = filterCriteriaDTO.getOrderStatus();
        if (orderStatus.equals(OrderStatus.ALL)) {
            orderStatus = null;
        }
        OrderManageRequestDTO requestDTO = new OrderManageRequestDTO(
                filterCriteriaDTO.getStartDate(),
                filterCriteriaDTO.getEndDate(),
                orderStatus
        );
        return orderFeignClient.getAdminOrderList(requestDTO, pageable);
    }

    public OrderPayDetailDTO getAdminOrderHistory(Long orderId) {
        return orderFeignClient.getAdminOrderDetails(orderId);
    }

}
