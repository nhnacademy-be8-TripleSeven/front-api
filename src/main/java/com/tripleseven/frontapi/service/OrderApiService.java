package com.tripleseven.frontapi.service;

import com.tripleseven.frontapi.client.OrderFeignClient;
import com.tripleseven.frontapi.dto.FilterCriteriaDTO;
import com.tripleseven.frontapi.dto.order.OrderManageRequestDTO;
import com.tripleseven.frontapi.dto.order.OrderManageResponseDTO;
import com.tripleseven.frontapi.dto.order.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderApiService {
    private final OrderFeignClient orderFeignClient;

    public List<OrderManageResponseDTO> getOrderHistories(int page, int size, String sort, FilterCriteriaDTO filterCriteriaDTO) {
        Status status = Status.valueOf(filterCriteriaDTO.getStatus());
        OrderManageRequestDTO requestDTO = new OrderManageRequestDTO(
                filterCriteriaDTO.getStartDate(),
                filterCriteriaDTO.getEndDate(),
                status
        );


        return orderFeignClient.getOrderList(page, size, sort, requestDTO);
    }

}
