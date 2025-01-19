package com.tripleseven.frontapi.controller.order;

import com.tripleseven.frontapi.dto.MemberDTO;
import com.tripleseven.frontapi.dto.order.*;
import com.tripleseven.frontapi.service.MemberService;
import com.tripleseven.frontapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Controller
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;

    @GetMapping("/frontend/order")
    public String getOrderPage(
            @RequestHeader(value = "X-USER",required = false)Long userId,
            @CookieValue(value = "GUEST-ID")String guestId,
            @RequestParam("type")String type,
            @RequestParam(value = "bookId", required = false) Long bookId,
            @RequestParam(value = "quantity",defaultValue = "1")int quantity,
            Model model) {
        List<ProductDTO>products = orderService.getProductsByType(type, bookId, quantity, userId, guestId);
        OrderCalculationResult orderInfo = orderService.calculateOrder(products,userId);
        List<WrappingResponseDTO> wrappingList = orderService.getAllWrappings();

        model.addAttribute("products", products);
        model.addAttribute("wrappingList", wrappingList);
        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("userId", userId);

        return "order/pay-user";
    }

    @GetMapping("/frontend/order/success")
    public String getOrderSuccessPage(
            @RequestHeader(value = "X-USER", required = false)Long userId,
            @CookieValue(value = "GUEST-ID",required = false)String guestId,
            @RequestParam Long orderId,
            Model model
    ){

        if(Objects.nonNull(userId)) {
            MemberDTO memberDTO = memberService.getMemberInfo(userId);
            OrderGroupResponseDTO order = orderService.getOrderGroup(orderId);
            TotalOrderCompleteDTO totalOrder = orderService.getOrderCompleteDTO(order);

            model.addAttribute("user",memberDTO);
            model.addAttribute("order",order);
            model.addAttribute("totalOrder",totalOrder);
            model.addAttribute("finalAmount", orderService.getPayPrice(orderId));

            return "order/pay-success";
        }
        else{
            OrderGroupResponseDTO order = orderService.getOrderGroup(orderId);
            TotalOrderCompleteDTO totalOrder = orderService.getOrderCompleteDTO(order);

            model.addAttribute("order",order);
            model.addAttribute("totalOrder", totalOrder);
            model.addAttribute("finalAmount", orderService.getPayPrice(orderId));
            return "order/pay-success-guest";
        }

    }
}
