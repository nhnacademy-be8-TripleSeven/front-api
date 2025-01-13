package com.tripleseven.frontapi.controller.order;

import com.tripleseven.frontapi.dto.MemberDTO;
import com.tripleseven.frontapi.dto.coupon.CouponDetailsDTO;
import com.tripleseven.frontapi.dto.order.ProductDTO;
import com.tripleseven.frontapi.service.BookService;
import com.tripleseven.frontapi.service.MemberService;
import com.tripleseven.frontapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class OrderController {

    private final OrderService orderService;
    private final BookService bookService;
    private final MemberService memberService;

    @GetMapping("/frontend/order")
    public String getOrderPage(
            @RequestHeader(value = "X-USER",required = false)Long userId,
            @CookieValue(value = "GUEST-ID")String guestId,
            @RequestParam("type")String type,
            @RequestParam(value = "bookId", required = false) Long bookId,
            Model model) {
        List<ProductDTO>products = List.of();
        //도서 상세페이지에서 구매 버튼을 누른 경우
        if("direct".equals(type)) {
            ProductDTO product = orderService.getProductInfoByDirect(bookId,1);
            products = List.of(product);
            model.addAttribute("products",product);
        }//장바구니 페이지에서 구매 버튼을 누른 경우
        else if("cart".equals(type)) {
            products = orderService.getProductInfoByCart();
        }


        // 총 상품 금액 및 할인 금액 계산
        int productAmount = products.stream()
                .mapToInt(p -> p.getPrice() * p.getQuantity())
                .sum();

        // 할인 금액 (얼마가 할인되는지)
        int discountAmount = products.stream()
                .mapToInt(p -> (p.getPrice() - p.getDiscountedPrice()) * p.getQuantity())
                .sum();

        int finalAmount = productAmount - discountAmount;
        int additionalAmount = finalAmount < 30000 ? 5000 : 0; //30000은 임시, order-api에서 배송정책 조회해서 가져와야함
        int totalAmount = finalAmount + additionalAmount;

        int availablePoint = 30000; // 만약 회원이라면 order-api에서 조회와야함 회원 아니면 조회 x

//        if(userId != null) {
//            availablePoint = orderService.getPoints(userId);
//        }

//        List<CouponDetailsDTO> couponList = bookService.getUnusedCoupons(userId);

        model.addAttribute("products", products);
        model.addAttribute("productAmount", productAmount);
        model.addAttribute("discountAmount", discountAmount);
        model.addAttribute("finalAmount", finalAmount);
        model.addAttribute("additionalAmount", additionalAmount);
        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("availablePoint", availablePoint);
//        model.addAttribute("couponList", couponList);

        return "order/pay-user";
    }

    @GetMapping("/frontend/order/success")
    public String getOrderSuccessPage(
            @RequestHeader(value = "X-USER", required = false)Long userId,
            @CookieValue(value = "GUEST-ID",required = false)String guestId,
            Model model
    ){
//        MemberDTO memberDTO = memberService.getMemberInfo(userId);
        MemberDTO memberDTO = new MemberDTO(1L,"nhn1234@gmail.com","010-1234-1234","마르코", Date.valueOf(LocalDate.now()),"man","gold");
        model.addAttribute("user",memberDTO);


        return "order/pay-success";

    }
}
