package com.tripleseven.frontapi.controller.order;

import com.tripleseven.frontapi.dto.FilterCriteriaDTO;
import com.tripleseven.frontapi.dto.order.OrderManageResponseDTO;
import com.tripleseven.frontapi.service.OrderApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
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

    @GetMapping("/order/history")
    public String orderHistories(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @Nullable @ModelAttribute FilterCriteriaDTO updateFilter,
            Model model) {

        FilterCriteriaDTO filterCriteriaDTO = new FilterCriteriaDTO();

        if (Objects.nonNull(updateFilter)) {
            filterCriteriaDTO.setStartDate(updateFilter.getStartDate());
            filterCriteriaDTO.setEndDate(updateFilter.getEndDate());
            filterCriteriaDTO.setStatus(updateFilter.getStatus());
        }

        model.addAttribute("filterCriteria", filterCriteriaDTO);

        List<OrderManageResponseDTO> orders = orderApiService.getOrderHistories(page, size, "test",filterCriteriaDTO);

        model.addAttribute("orders", orders);

        return "order-manage";
    }
}
