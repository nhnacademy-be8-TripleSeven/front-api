package com.tripleseven.frontapi.controller.mypage;

import com.tripleseven.frontapi.dto.FilterCriteriaDTO;
import com.tripleseven.frontapi.dto.order.OrderManageResponseDTO;
import com.tripleseven.frontapi.dto.order.Status;
import com.tripleseven.frontapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public String getOrderHistory(@RequestParam("orderId") Long orderId) {

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
