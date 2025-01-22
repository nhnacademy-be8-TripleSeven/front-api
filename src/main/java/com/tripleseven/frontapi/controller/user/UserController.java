package com.tripleseven.frontapi.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {

    @GetMapping("/api/frontend/member/my-page/info")
    public String getMyPage() {
        return "/member/my-page/info";
    }

    @GetMapping("/frontend/guest/orders/search")
    public String searchGuestOrder() {
        return "/guest/order/search";
    }

    @GetMapping("/frontend/guest/orders")
    public String getGuestOrderDetails() {
        return "/guest/order/detail";
    }

    @GetMapping("/frontend/guest/find/order-number")
    public String findGuestOrderNumbers() {
        return "/guest/search/order-number";
    }

    @GetMapping("/frontend/guest/find/order-password")
    public String findGuestOrderPassword() {
        return "/guest/search/order-password";
    }

    @GetMapping("/frontend/member/my-page/order/manage")
    public String manageMemberOrders() {
        return "/member/my-page/order/manage";
    }

    @GetMapping("/frontend/member/my-page/order/refund-exchange")
    public String getRefundExchangeDetails() {
        return "/member/my-page/order/refund-exchange";
    }

    @GetMapping("/frontend/member/my-page/like")
    public String getMyLikeBooks() {
        return "/member/my-page/like/like";
    }

    @GetMapping("/frontend/member/my-page/points/use")
    public String getMyPointUseHistory() {
        return "/member/my-page/point-history/use";
    }

    @GetMapping("/frontend/member/my-page/points/earn")
    public String getMyPointEarnHistory() {
        return "/member/my-page/point-history/earn";
    }

    @GetMapping("/frontend/member/my-page/coupons")
    public String getCouponDetails() {
        return "/member/my-page/coupon/detail";
    }


}