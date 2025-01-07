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

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class OrderManageController {
    private final OrderService orderService;

    @GetMapping("/frontend/orders/history")
    public String getOrderHistories(
            @RequestHeader("X-USER") Long userId,
            @RequestParam(value = "startDate", required = false) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) LocalDate endDate,
            @RequestParam(value = "orderStatus", required = false) OrderStatus orderStatus,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
    ) {

        FilterCriteriaDTO filterCriteriaDTO = createFilterCriteria(startDate, endDate, orderStatus);

        Pageable pageable = PageRequest.of(page, size);
        Page<OrderManageResponseDTO> orderPages = orderService.getOrderHistories(filterCriteriaDTO, userId, pageable);

        model.addAttribute("status", filterCriteriaDTO.getOrderStatus().name());
        model.addAttribute("orders", orderPages.getContent());
        model.addAttribute("page", pageable);
        model.addAttribute("totalPage", orderPages.getTotalPages());

        return "order-history";
    }


    @GetMapping("/frontend/orders/history/{orderId}")
    public String getOrderHistory(
            @RequestHeader("X-USER") Long userId,
            @PathVariable("orderId") Long orderId,
            Model model
    ) {
        OrderPayDetailDTO orderDetail = orderService.getOrderHistory(userId, orderId);
        List<OrderInfoDTO> orderInfos = orderDetail.getOrderInfos();
        OrderGroupInfoDTO orderGroupInfo = orderDetail.getOrderGroupInfoDTO();
        DeliveryInfoDTO deliveryInfo = orderDetail.getDeliveryInfo();
        OrderPayInfoDTO orderPayInfo = orderDetail.getOrderPayInfoDTO();

        model.addAttribute("orderGroupInfo", orderGroupInfo);
        model.addAttribute("orderInfos", orderInfos);
        model.addAttribute("deliveryInfo", deliveryInfo);
        model.addAttribute("orderPayInfo", orderPayInfo);

        return "order-history-detail";
    }


    @GetMapping("/frontend/orders/refund")
    public String getRefundHistories(
            @RequestHeader("X-USER") Long userId,
            @RequestParam(value = "startDate", required = false) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
    ) {

        FilterCriteriaDTO filterCriteriaDTO = createFilterCriteria(startDate, endDate, OrderStatus.RETURNED);

        Pageable pageable = PageRequest.of(page, size);

        Page<OrderManageResponseDTO> orderPages = orderService.getOrderHistories(filterCriteriaDTO, userId, pageable);

        model.addAttribute("orders", orderPages.getContent());
        model.addAttribute("page", pageable);
        model.addAttribute("totalPage", orderPages.getTotalPages());

        return "refund-history";
    }

    @GetMapping("/frontend/orders/canceled")
    public String getCanceledHistories(
            @RequestHeader("X-USER") Long userId,
            @RequestParam(value = "startDate", required = false) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
    ) {

        FilterCriteriaDTO filterCriteriaDTO = createFilterCriteria(startDate, endDate, OrderStatus.ORDER_CANCELED);

        Pageable pageable = PageRequest.of(page, size);

        Page<OrderManageResponseDTO> orderPages = orderService.getOrderHistories(filterCriteriaDTO, userId, pageable);

        model.addAttribute("orders", orderPages.getContent());
        model.addAttribute("page", pageable);
        model.addAttribute("totalPage", orderPages.getTotalPages());

        return "cancel-history";
    }

    @PostMapping("/frontend/orders/change-return")
    public void returnOrderHistories(
            @RequestHeader("X-USER") Long userId,
            @RequestParam("orderIds") List<Long> ids
    ) {
        orderService.updateOrderHistories(userId, ids, OrderStatus.RETURNED_PENDING);
    }

    @PostMapping("/frontend/orders/change-cancel")
    public void cancelOrderHistories(
            @RequestHeader("X-USER") Long userId,
            @RequestParam("orderIds") List<Long> ids
    ) {
        orderService.updateOrderHistories(userId, ids, OrderStatus.ORDER_CANCELED);
    }


    private FilterCriteriaDTO createFilterCriteria(LocalDate startDate, LocalDate endDate, OrderStatus orderStatus) {
        FilterCriteriaDTO filterCriteriaDTO = new FilterCriteriaDTO();
        filterCriteriaDTO.setStartDate(Objects.nonNull(startDate) ? startDate : LocalDate.now().minusMonths(1));
        filterCriteriaDTO.setEndDate(Objects.nonNull(endDate) ? endDate : LocalDate.now());
        filterCriteriaDTO.setOrderStatus(Objects.nonNull(orderStatus) ? orderStatus : OrderStatus.ALL);
        return filterCriteriaDTO;
    }

}
