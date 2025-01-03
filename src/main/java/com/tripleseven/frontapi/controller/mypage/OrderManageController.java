package com.tripleseven.frontapi.controller.mypage;

import com.tripleseven.frontapi.dto.FilterCriteriaDTO;
import com.tripleseven.frontapi.dto.order.*;
import com.tripleseven.frontapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class OrderManageController {
    private final OrderService orderService;

    @GetMapping("/frontend/orders/history")
    public String getOrderHistories(
            @ModelAttribute FilterCriteriaDTO updateFilter,
            @RequestHeader("X-USER") Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        FilterCriteriaDTO filterCriteriaDTO = new FilterCriteriaDTO();
        if (Objects.nonNull(updateFilter.getStartDate())) {
            filterCriteriaDTO.setStartDate(updateFilter.getStartDate());
            filterCriteriaDTO.setEndDate(updateFilter.getEndDate());
            filterCriteriaDTO.setStatus(updateFilter.getStatus());
        }

        Pageable pageable = PageRequest.of(page, size);

        Page<OrderManageResponseDTO> orderPages = orderService.getOrderHistories(filterCriteriaDTO, userId, pageable);
        List<OrderManageResponseDTO> orders = orderPages.getContent();

        model.addAttribute("filterCriteria", filterCriteriaDTO);
        model.addAttribute("orders", orders);
        model.addAttribute("page", pageable);
        model.addAttribute("totalPage", orderPages.getTotalPages());

        return "order-history";
    }

    @GetMapping("/frontend/orders/history/{orderId}")
    public String getOrderHistory(
            @RequestHeader("X-USER") Long userId,
            @PathVariable("orderId") Long orderId,
            Model model) {
        OrderPayDetailDTO orderDetail = orderService.getOrderHistory(userId, orderId);
        List<OrderInfoDTO> orderInfos = orderDetail.getOrderInfos();
        DeliveryInfoDTO deliveryInfo = orderDetail.getDeliveryInfo();
        OrderPayInfoDTO orderPayInfo = orderDetail.getOrderPayInfoDTO();

        model.addAttribute("orderInfos", orderInfos);
        model.addAttribute("deliveryInfo", deliveryInfo);
        model.addAttribute("OrderPayInfo", orderPayInfo);

        return "order-history-detail";
    }


    @GetMapping("/frontend/orders/refund")
    public String getRefundHistories(
            @ModelAttribute FilterCriteriaDTO updateFilter,
//            @RequestHeader("X-USER") Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        FilterCriteriaDTO filterCriteriaDTO = new FilterCriteriaDTO();
        if (Objects.nonNull(updateFilter.getStartDate())) {
            filterCriteriaDTO.setStartDate(updateFilter.getStartDate());
            filterCriteriaDTO.setEndDate(updateFilter.getEndDate());
        }
        filterCriteriaDTO.setStatus(Status.RETURNED);

        Pageable pageable = PageRequest.of(page, size);

        Page<OrderManageResponseDTO> orderPages = orderService.getOrderHistories(filterCriteriaDTO, 1L, pageable);
        List<OrderManageResponseDTO> orders = orderPages.getContent();

        model.addAttribute("filterCriteria", filterCriteriaDTO);
        model.addAttribute("orders", orders);
        model.addAttribute("page", pageable);
        model.addAttribute("totalPage", orderPages.getTotalPages());

        return "refund-history";
    }

    @GetMapping("/frontend/orders/canceled")
    public String getCanceledHistories(
//            @RequestHeader("X-USER") Long userId,
            @ModelAttribute FilterCriteriaDTO updateFilter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        FilterCriteriaDTO filterCriteriaDTO = new FilterCriteriaDTO();
        if (Objects.nonNull(updateFilter.getStartDate())) {
            filterCriteriaDTO.setStartDate(updateFilter.getStartDate());
            filterCriteriaDTO.setEndDate(updateFilter.getEndDate());
        }
        filterCriteriaDTO.setStatus(Status.ORDER_CANCELED);

        Pageable pageable = PageRequest.of(page, size);

        Page<OrderManageResponseDTO> orderPages = orderService.getOrderHistories(filterCriteriaDTO, 1L, pageable);
        List<OrderManageResponseDTO> orders = orderPages.getContent();

        model.addAttribute("filterCriteria", filterCriteriaDTO);
        model.addAttribute("orders", orders);
        model.addAttribute("page", pageable);
        model.addAttribute("totalPage", orderPages.getTotalPages());

        return "cancel-history";
    }
}
