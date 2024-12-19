package com.tripleseven.frontapi.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/frontend")
public class AdminCouponController {

    @GetMapping("/coupon-policy/create")
    public String createCouponPolicy() {
        return "/admin/coupon-policy/create";
    }

    @GetMapping("/coupon-policy/detail")
    public String getCouponPolicyPage() {
        return "/admin/coupon-policy/detail";
    }

    @GetMapping("/coupons/manage")
    public String getCouponManagePage() {
        return "/admin/coupon/manage";
    }
}
