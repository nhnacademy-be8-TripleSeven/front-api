
package com.tripleseven.frontapi.controller.admin;

import com.tripleseven.frontapi.client.BookFeignClient;
import com.tripleseven.frontapi.dto.coupon.CouponPolicyRequestDTO;
import com.tripleseven.frontapi.dto.coupon.CouponPolicyResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/admin/frontend")
@RequiredArgsConstructor
public class AdminCouponController {

    private final BookFeignClient couponPolicyFeignClient;

//    @GetMapping("/coupon-policy/create")
//    public String createCouponPolicy() {
//        return "/admin/coupon-policy-create";
//    }
//
//    @GetMapping("/coupon-policy/detail")
//    public String getCouponPolicyPage() {
//        return "/admin/coupon-policy/detail";
//    }
//
//    @GetMapping("/coupons/manage")
//    public String getCouponManagePage() {
//        return "/admin/coupon/manage";
//    }
//
//    @GetMapping("/coupon-policy-register")
//    public String getCouponPolicyRegisterPage() {
//        return "/admin/coupon-policy-register";
//    }




    // 테스트용 쿠폰 정책 조회 화면
    @GetMapping("/check-coupon-policy")
    public String showCouponPolicyList(Model model) {
        // 전체 쿠폰 정책을 불러와 모델에 추가
        List<CouponPolicyResponseDTO> mockPolicies = List.of(
                new CouponPolicyResponseDTO(1L, "Test Coupon 1", 1000L, 5000L, BigDecimal.valueOf(15), null, 30),
                new CouponPolicyResponseDTO(2L, "Test Coupon 2", 2000L, 8000L, null, 2000L, 60)
        );
        model.addAttribute("policies", mockPolicies);
        return "/admin/check-coupon-policy";
    }

    // 모든 쿠폰 정책 조회 페이지
    @GetMapping("/coupon-policy/list")
    public String listCouponPolicies(Model model) {
        List<CouponPolicyResponseDTO> policies = couponPolicyFeignClient.getAllCouponPolicies();
        model.addAttribute("policies", policies);
        return "/admin/check-coupon-policy";
    }

    // 쿠폰 정책 생성 페이지
    @GetMapping("/coupon-policy/register")
    public String showChockCouponPolicyPage(Model model) {
        model.addAttribute("couponPolicy", new CouponPolicyRequestDTO());
        return "/admin/coupon-policy-create";
    }

    // 쿠폰 정책 등록
    @PostMapping("/coupon-policy/register")
    public String createCouponPolicy(@ModelAttribute CouponPolicyRequestDTO couponPolicy, Model model) {
        CouponPolicyResponseDTO response = couponPolicyFeignClient.createCouponPolicy(couponPolicy);
        model.addAttribute("response", response);
        return "redirect:/admin/frontend/coupon-policy/success";
    }

    // 쿠폰 정책 검색
    @GetMapping("/coupon-policy/search")
    @ResponseBody
    public List<CouponPolicyResponseDTO> searchCouponPolicies(@RequestParam(required = false) String query) {
        return query == null || query.isBlank()
                ? couponPolicyFeignClient.getAllCouponPolicies() // 전체 조회
                : couponPolicyFeignClient.searchCouponPoliciesByName(query); // 검색 결과
    }

    // 쿠폰 정책 수정 페이지
    @GetMapping("/coupon-policy/update/{id}")
    public String showUpdatePage(@PathVariable Long id, Model model) {
        CouponPolicyResponseDTO policy = couponPolicyFeignClient.getCouponPolicyById(id);
        model.addAttribute("policy", policy);
        return "/admin/coupon-policy-update";
    }

    // 쿠폰 정책 수정 처리
    @PostMapping("/coupon-policy/update/{id}")
    public String updateCouponPolicy(@PathVariable Long id, @ModelAttribute CouponPolicyRequestDTO request) {
        couponPolicyFeignClient.updateCouponPolicy(id, request);
        return "redirect:/admin/frontend/coupon-policy/list";
    }

    // 쿠폰 정책 삭제
    @PostMapping("/coupon-policy/delete/{id}")
    public String deleteCouponPolicy(@PathVariable Long id) {
        couponPolicyFeignClient.deleteCouponPolicy(id);
        return "redirect:/admin/frontend/coupon-policy/list";
    }

}
