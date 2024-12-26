
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

    private final BookFeignClient bookFeignClient;

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
        List<CouponPolicyResponseDTO> policies = bookFeignClient.getAllCouponPolicies();
        model.addAttribute("policies", policies);
        return "/admin/check-coupon-policy";
    }

    // 쿠폰 정책 검색
    @GetMapping("/coupon-policy/search")
    @ResponseBody
    public List<CouponPolicyResponseDTO> searchCouponPolicies(@RequestParam(required = false) String query) {
        return query == null || query.isBlank()
                ? bookFeignClient.getAllCouponPolicies() // 전체 조회
                : bookFeignClient.searchCouponPoliciesByName(query); // 검색 결과
    }

    // 쿠폰 정책 수정 페이지
    @GetMapping("/coupon-policy/update/{id}")
    public String showUpdatePage(@PathVariable Long id, Model model) {
        CouponPolicyResponseDTO policy = bookFeignClient.getCouponPolicyById(id);
        model.addAttribute("policy", policy);
        return "/admin/coupon-policy-update";
    }

    // 쿠폰 정책 수정 처리
    @PostMapping("/coupon-policy/update/{id}")
    public String updateCouponPolicy(@PathVariable Long id, @ModelAttribute CouponPolicyRequestDTO request) {
        bookFeignClient.updateCouponPolicy(id, request);
        return "redirect:/admin/frontend/coupon-policy/list";
    }

    // 쿠폰 정책 삭제
    @PostMapping("/coupon-policy/delete/{id}")
    public String deleteCouponPolicy(@PathVariable Long id) {
        bookFeignClient.deleteCouponPolicy(id);
        return "redirect:/admin/frontend/coupon-policy/list";
    }





    /**
     * 쿠폰 정책 생성 페이지로 이동
     */
    @GetMapping("/coupon-policy/register")
    public String showCouponPolicyRegisterPage(Model model) {
        // 새로운 쿠폰 정책 DTO를 모델에 추가하여 폼과 연동
        model.addAttribute("couponPolicy", new CouponPolicyRequestDTO());
        return "/admin/coupon-policy-create";
    }

    /**
     * 쿠폰 정책 생성 요청 처리
     */
    @PostMapping("/coupon-policy/register")
    @ResponseBody
    public String createCouponPolicy(@ModelAttribute CouponPolicyRequestDTO couponPolicy) {
        // 할인율 또는 할인 금액 처리
        if (couponPolicy.getCouponDiscountRate() != null && couponPolicy.getCouponDiscountRate().compareTo(BigDecimal.ZERO) > 0) {
            // 할인율 설정 (0.25 -> 25% 변환)
            couponPolicy.setCouponDiscountRate(couponPolicy.getCouponDiscountRate().divide(new BigDecimal(100)));
            couponPolicy.setCouponDiscountAmount(null); // 금액은 null로 설정
        } else if (couponPolicy.getCouponDiscountAmount() != null && couponPolicy.getCouponDiscountAmount() > 0) {
            // 할인 금액 설정
            couponPolicy.setCouponDiscountRate(null); // 할인율은 null로 설정
        } else {
            throw new IllegalArgumentException("할인 금액 또는 할인율이 올바르지 않습니다.");
        }

        bookFeignClient.createCouponPolicy(couponPolicy);

        // 성공 메시지를 반환
        return "쿠폰 정책이 성공적으로 등록되었습니다.";
    }


}
