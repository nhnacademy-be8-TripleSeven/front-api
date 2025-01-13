package com.tripleseven.frontapi.controller.order;

import com.tripleseven.frontapi.client.OrderFeignClient;
import com.tripleseven.frontapi.dto.order.OrderDetailResponseDTO;
import com.tripleseven.frontapi.dto.order.OrderGroupResponseDTO;
import com.tripleseven.frontapi.dto.order.OrderManageResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GuestOrderController {
    private final OrderFeignClient orderFeignClient;

    @GetMapping("/frontend/guest/order-histories")
    public String viewGuestOrdersPage(@RequestParam(required = false) String phone, Model model) {
        model.addAttribute("phone", phone); 
        return "guest-history";
    }


    @GetMapping("/frontend/guest/order-histories/list")
    public ResponseEntity<List<OrderGroupResponseDTO>> getGuestOrders(@RequestParam String phone) {
        List<OrderGroupResponseDTO> orders = orderFeignClient.getGuestOrderGroups(phone);
        return ResponseEntity.ok(orders); // 조회된 주문 데이터 반환
    }


    @GetMapping("/frontend/guest/order-details/{orderGroupId}")
    public ResponseEntity<List<OrderDetailResponseDTO>> getOrderDetailsByGroupId(@PathVariable Long orderGroupId) {
        List<OrderDetailResponseDTO> details = orderFeignClient.getOrderDetailsByOrderGroupId(orderGroupId);
        return ResponseEntity.ok(details); // 조회된 주문 상세 목록 반환
    }
}
