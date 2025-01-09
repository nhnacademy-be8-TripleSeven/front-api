package com.tripleseven.frontapi.controller.admin;

import com.tripleseven.frontapi.client.OrderFeignClient;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/admin/frontend/orders")
@RequiredArgsConstructor
public class AdminStatusController {
    private final OrderService orderService;

    @GetMapping("/status")
    public String getAdminOrderDetails(
            @RequestParam(value = "startDate", required = false) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) LocalDate endDate,
            @RequestParam(value = "orderStatus", required = false) OrderStatus orderStatus,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
    ) {

        FilterCriteriaDTO filterCriteriaDTO = createFilterCriteria(startDate, endDate, orderStatus);

        Pageable pageable = PageRequest.of(page, size);
        Page<OrderManageResponseDTO> orderPages = orderService.getAdminOrderHistories(filterCriteriaDTO, pageable);

        model.addAttribute("status", filterCriteriaDTO.getOrderStatus().name());
        model.addAttribute("orders", orderPages.getContent());
        model.addAttribute("page", pageable);
        model.addAttribute("totalPage", orderPages.getTotalPages());
        return "admin/order-manage";
    }

    @GetMapping("/status/{orderId}")
    public String getOrderHistory(
            @PathVariable("orderId") Long orderId,
            Model model
    ) {
        OrderPayDetailDTO orderDetail = orderService.getAdminOrderHistory(orderId);

        List<OrderInfoDTO> orderInfos = orderDetail.getOrderInfos();
        OrderGroupInfoDTO orderGroupInfo = orderDetail.getOrderGroupInfoDTO();
        DeliveryInfoDTO deliveryInfo = orderDetail.getDeliveryInfo();
        OrderPayInfoDTO orderPayInfo = orderDetail.getOrderPayInfoDTO();

        model.addAttribute("orderGroupInfo", orderGroupInfo);
        model.addAttribute("orderInfos", orderInfos);
        model.addAttribute("deliveryInfo", deliveryInfo);
        model.addAttribute("orderPayInfo", orderPayInfo);

        return "admin/order-manage-detail";
    }

    @GetMapping("/refund")
    public String getAdminOrderRefunds(
            @RequestParam(value = "startDate", required = false) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
    ) {

        FilterCriteriaDTO filterCriteriaDTO = createFilterCriteria(startDate, endDate, OrderStatus.RETURNED_PENDING);

        Pageable pageable = PageRequest.of(page, size);
        Page<OrderManageResponseDTO> orderPages = orderService.getAdminOrderHistories(filterCriteriaDTO, pageable);

        model.addAttribute("status", filterCriteriaDTO.getOrderStatus().name());
        model.addAttribute("orders", orderPages.getContent());
        model.addAttribute("page", pageable);
        model.addAttribute("totalPage", orderPages.getTotalPages());
        return "admin/order-manage-refund";
    }


    private FilterCriteriaDTO createFilterCriteria(LocalDate startDate, LocalDate endDate, OrderStatus orderStatus) {
        FilterCriteriaDTO filterCriteriaDTO = new FilterCriteriaDTO();
        filterCriteriaDTO.setStartDate(Objects.nonNull(startDate) ? startDate : LocalDate.now().minusMonths(1));
        filterCriteriaDTO.setEndDate(Objects.nonNull(endDate) ? endDate : LocalDate.now());
        filterCriteriaDTO.setOrderStatus(Objects.nonNull(orderStatus) ? orderStatus : OrderStatus.ALL);
        return filterCriteriaDTO;
    }

}
