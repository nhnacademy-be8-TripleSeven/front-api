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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class OrderManageController {
    private final OrderService orderService;

    @GetMapping("/orders/history")
    public String getOrderHistories(
            @ModelAttribute FilterCriteriaDTO updateFilter,
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

        Page<OrderManageResponseDTO> orderPages = orderService.getOrderHistories(filterCriteriaDTO, pageable);
        List<OrderManageResponseDTO> orders = orderPages.getContent();

        model.addAttribute("filterCriteria", filterCriteriaDTO);
        model.addAttribute("orders", orders);
        model.addAttribute("page", pageable);
        model.addAttribute("totalPage", orderPages.getTotalPages());

        return "order-history";
    }

    @GetMapping("/orders/history/{orderId}")
    public String getOrderHistory(@PathVariable("orderId") Long orderId,
                                  Model model) {
//        OrderDetailDTO orderDetail = orderService.getOrderHistory(orderId);
//        List<OrderInfoDTO> orderInfos = orderDetail.getOrderInfos();
//        DeliveryInfoDTO deliveryInfo = orderDetail.getDeliveryInfo();
//        PayInfoDTO payInfo = orderDetail.getPayInfo();
//
//        model.addAttribute("orderInfos", orderInfos);
//        model.addAttribute("deliveryInfo", deliveryInfo);
//        model.addAttribute("payInfo", payInfo);

        return "order-history-detail";
    }


    @GetMapping("/orders/refund")
    public String getRefundHistories(
            @ModelAttribute FilterCriteriaDTO updateFilter,
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

        Page<OrderManageResponseDTO> orderPages = orderService.getOrderHistories(filterCriteriaDTO, pageable);
        List<OrderManageResponseDTO> orders = orderPages.getContent();

        model.addAttribute("filterCriteria", filterCriteriaDTO);
        model.addAttribute("orders", orders);
        model.addAttribute("page", pageable);
        model.addAttribute("totalPage", orderPages.getTotalPages());

        return "refund-history";
    }

    @GetMapping("/orders/canceled")
    public String getCanceledHistories(
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

        Page<OrderManageResponseDTO> orderPages = orderService.getOrderHistories(filterCriteriaDTO, pageable);
        List<OrderManageResponseDTO> orders = orderPages.getContent();

        model.addAttribute("filterCriteria", filterCriteriaDTO);
        model.addAttribute("orders", orders);
        model.addAttribute("page", pageable);
        model.addAttribute("totalPage", orderPages.getTotalPages());

        return "cancel-history";
    }
}
