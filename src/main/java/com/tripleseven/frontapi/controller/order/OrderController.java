package com.tripleseven.frontapi.controller.order;

import com.tripleseven.frontapi.dto.MemberDTO;
import com.tripleseven.frontapi.dto.order.OrderCalculationResult;
import com.tripleseven.frontapi.dto.order.ProductDTO;
import com.tripleseven.frontapi.dto.order.WrappingResponseDTO;
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
        List<ProductDTO>products = orderService.getProductsByType(type, bookId, quantity);
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
        MemberDTO memberDTO = null;

        if(Objects.nonNull(userId)) {
            memberDTO = memberService.getMemberInfo(userId);
            model.addAttribute("user",memberDTO);
            return "order/pay-success";
        }
        else{

            return "order/pay-success-guest";
        }

    }
}
