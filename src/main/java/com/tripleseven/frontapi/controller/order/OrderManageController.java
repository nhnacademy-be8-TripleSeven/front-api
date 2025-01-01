package com.tripleseven.frontapi.controller.order;

import com.tripleseven.frontapi.dto.FilterCriteriaDTO;
import com.tripleseven.frontapi.dto.order.OrderManageResponseDTO;
import com.tripleseven.frontapi.dto.order.Status;
import com.tripleseven.frontapi.service.OrderApiService;
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
    private final OrderApiService orderApiService;

    @GetMapping("/orders/manage")
    public String orderHistories(
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

        Page<OrderManageResponseDTO> orderPages = orderApiService.getOrderHistories(filterCriteriaDTO, pageable);
        List<OrderManageResponseDTO> orders = orderPages.getContent();

        model.addAttribute("filterCriteria", filterCriteriaDTO);
        model.addAttribute("orders", orders);
        model.addAttribute("page", pageable);
        model.addAttribute("totalPage", orderPages.getTotalPages());

        return "order-manage";
    }

    @GetMapping("/orders/refund")
    public String refundHistories(
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

        Page<OrderManageResponseDTO> orderPages = orderApiService.getOrderHistories(filterCriteriaDTO, pageable);
        List<OrderManageResponseDTO> orders = orderPages.getContent();

        model.addAttribute("filterCriteria", filterCriteriaDTO);
        model.addAttribute("orders", orders);
        model.addAttribute("page", pageable);
        model.addAttribute("totalPage", orderPages.getTotalPages());

        return "refund-history";
    }

    @GetMapping("/orders/canceled")
    public String canceledHistories(
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

        Page<OrderManageResponseDTO> orderPages = orderApiService.getOrderHistories(filterCriteriaDTO, pageable);
        List<OrderManageResponseDTO> orders = orderPages.getContent();

        model.addAttribute("filterCriteria", filterCriteriaDTO);
        model.addAttribute("orders", orders);
        model.addAttribute("page", pageable);
        model.addAttribute("totalPage", orderPages.getTotalPages());

        return "cancel-history";
    }
}
