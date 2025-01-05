package com.tripleseven.frontapi.controller.pay;

import com.tripleseven.frontapi.dto.pay.PayInfoRequestDTO;
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

    @PostMapping("/payment")
    public String paymentPage(
            Model model,
            @ModelAttribute PayInfoRequestDTO orderInfoRequestDTO) {

        // 주문 정보

        PayInfoRequestDTO responseDTO = orderService.getOrderInfo(orderInfoRequestDTO);
        // 멤버 api호출해서 필요한 정보 보내기?

        model.addAttribute("orderId", responseDTO.getOrderId());
        model.addAttribute("amount", responseDTO.getTotalAmount());
        model.addAttribute("orderName", "sample product");
        model.addAttribute("customerName", orderInfoRequestDTO.getCustomerName());
        model.addAttribute("customerEmail", orderInfoRequestDTO.getCustomerEmail());

        return "payment/checkout";
    }

    @GetMapping("/payment/success")
    public String paymentSuccessPage() {

        return "payment/success";
    }

    @PostMapping("/confirm/payment")
    public ResponseEntity<JSONObject>confirmPayment(@RequestBody String jsonBody){
        JSONObject response = orderService.getPayment(jsonBody);

        int statusCode = response.containsKey("error") ? 400 : 200;

        return ResponseEntity.status(statusCode).body(response);
    }

    @RequestMapping(value = "/fail", method = RequestMethod.GET)
    public String failPayment(HttpServletRequest request, Model model) {
        model.addAttribute("code", request.getParameter("code"));
        model.addAttribute("message", request.getParameter("message"));
        return "/pay-fail";
    }
}

