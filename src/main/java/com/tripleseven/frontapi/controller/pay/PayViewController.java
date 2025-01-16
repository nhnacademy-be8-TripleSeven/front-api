package com.tripleseven.frontapi.controller.pay;

import com.tripleseven.frontapi.dto.pay.PayInfoRequestDTO;
import com.tripleseven.frontapi.dto.pay.PayInfoResponseDTO;
import com.tripleseven.frontapi.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@RequiredArgsConstructor
@Controller
public class PayViewController {

    private final OrderService orderService;
//    @GetMapping("/order")
//    public String payTest(){
//        return "pay-test";
//    }

    @PostMapping("/frontend/payment")
    public String paymentPage(
            Model model,
            @ModelAttribute PayInfoRequestDTO payInfoRequestDTO,
            @RequestHeader(value = "X-USER",required = false)Long userId,
            @CookieValue(value = "GUEST-ID")String guestId) {

        // 주문 정보
            PayInfoResponseDTO responseDTO = orderService.getPayInfo(userId,guestId,payInfoRequestDTO);

        // 멤버 api호출해서 필요한 정보 보내기?

        model.addAttribute("orderId", responseDTO.getOrderId());
        model.addAttribute("amount", responseDTO.getTotalAmount());
        model.addAttribute("orderName", "sample product");
        model.addAttribute("customerName", payInfoRequestDTO.getRecipientInfo().getRecipientName());
        model.addAttribute("customerEmail", "");

        return "payment/checkout";
    }

    @GetMapping("/frontend/payment/success")
    public String paymentSuccessPage(
            @RequestHeader(value = "X-USER",required = false)Long userId,
            @CookieValue(value = "GUEST-ID")String guestId
    ) {

        return "payment/success";
    }

    @PostMapping("/frontend/confirm/payment")
    public ResponseEntity<JSONObject>confirmPayment(
            @RequestBody String jsonBody,
            @RequestHeader(value = "X-USER",required = false)Long userId,
            @CookieValue(value = "GUEST-ID")String guestId){
        JSONObject response = orderService.getPayment(userId, guestId,jsonBody);

        int statusCode = response.containsKey("error") ? 400 : 200;

        return ResponseEntity.status(statusCode).body(response);
    }

    @RequestMapping(value = "/frontend/payment/fail", method = RequestMethod.GET)
    public String failPayment(HttpServletRequest request, Model model,
                              @RequestHeader(value = "X-USER",required = false)Long userId,
                              @CookieValue(value = "GUEST-ID")String guestId) {
        model.addAttribute("code", request.getParameter("code"));
        model.addAttribute("message", request.getParameter("message"));
        return "payment/fail";
    }
}

