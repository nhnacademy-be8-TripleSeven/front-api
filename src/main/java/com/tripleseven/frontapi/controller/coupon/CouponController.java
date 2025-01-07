package com.tripleseven.frontapi.controller.coupon;

import com.tripleseven.frontapi.client.BookFeignClient;
import com.tripleseven.frontapi.dto.coupon.CouponDetailsDTO;
import com.tripleseven.frontapi.dto.coupon.CouponPolicyRequestDTO;
import com.tripleseven.frontapi.dto.coupon.CouponPolicyResponseDTO;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@Slf4j
@Controller
@RequiredArgsConstructor
public class CouponController {

    private final BookFeignClient bookFeignClient;

    // 운영 환경 (디폴트 값 없이 헤더 강제)
    @GetMapping("/api/frontend/coupons/history")
    public String getCouponHistory(@RequestHeader("X-USER") Long userId,
                                   @RequestParam(required = false) String keyword,
                                   @RequestParam(required = false) String startDate,
                                   @RequestParam(required = false) String endDate,
                                   Model model) {

        log.info("X-USER {}",userId);
        // 발급 내역 조회
        List<CouponDetailsDTO> issuedCoupons = bookFeignClient.getAllCoupons(userId, keyword, startDate, endDate);

        // 사용 내역 조회
        List<CouponDetailsDTO> usedCoupons = bookFeignClient.getUsedCoupons(userId, keyword, startDate, endDate);

        issuedCoupons.forEach(this::convertStatusToKorean);
        usedCoupons.forEach(this::convertStatusToKorean);

        model.addAttribute("issuedCoupons", issuedCoupons);
        model.addAttribute("usedCoupons", usedCoupons);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("keyword", keyword);

        return "coupon-history";
    }


    private void convertStatusToKorean(CouponDetailsDTO coupon) {
        switch (coupon.getStatus()) {
            case "USED":
                coupon.setStatus("사용");
                break;
            case "NOTUSED":
                coupon.setStatus("미사용");
                break;
            case "EXPIRED":
                coupon.setStatus("만료");
                break;
        }
    }





    // 테스트 환경 (defaultValue 제공)
    @GetMapping("/frontend/coupons/history")
    public String getCouponHistoryForTest(@RequestHeader(value = "X-USER", defaultValue = "1") Long userId,
                                          @RequestParam(required = false) String keyword,
                                          @RequestParam(required = false) String startDate,
                                          @RequestParam(required = false) String endDate,
                                          Model model) {
        // 발급 내역 조회
        List<CouponDetailsDTO> issuedCoupons = bookFeignClient.getAllCoupons(userId, keyword, startDate, endDate);

        // 사용 내역 조회
        List<CouponDetailsDTO> usedCoupons = bookFeignClient.getUsedCoupons(userId, keyword, startDate, endDate);

        issuedCoupons.forEach(this::convertStatusToKorean);
        usedCoupons.forEach(this::convertStatusToKorean);

        model.addAttribute("issuedCoupons", issuedCoupons);
        model.addAttribute("usedCoupons", usedCoupons);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("keyword", keyword);

        return "coupon-history";
    }

    // 테스트용 쿠폰 생성
    @GetMapping("/frontend/coupons/create")
    public String showCouponCreatePageTest() {
        return "/admin/coupon-create";
    }

    // 테스트용 정책 등록
    @GetMapping("/frontend/coupon-policies/register")
    public String showCouponPolicyRegisterPageTest(Model model) {
        // 새로운 쿠폰 정책 DTO를 모델에 추가하여 폼과 연동
        model.addAttribute("couponPolicy", new CouponPolicyRequestDTO());
        return "/admin/coupon-policy-create";
    }

    // 테스트용 정책 리스트
    @GetMapping("frontend/coupon-policies/list")
    public String listCouponPolicies(@RequestParam(required = false) String query, Model model) {
        List<CouponPolicyResponseDTO> policies;
        try {
            // 검색어가 없으면 전체 조회, 있으면 검색
            policies = (query == null || query.isBlank())
                    ? bookFeignClient.getAllCouponPolicies()
                    : bookFeignClient.searchCouponPoliciesByName(query);
        } catch (FeignException.NotFound e) {
            // 쿠폰 정책이 없을 때 빈 리스트 반환
            policies = Collections.emptyList();
        }

        model.addAttribute("policies", policies);
        model.addAttribute("query", query); // 검색어 유지
        return "/admin/check-coupon-policy";
    }

}

