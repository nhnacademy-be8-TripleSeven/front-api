package com.tripleseven.frontapi.controller.coupon;

import com.tripleseven.frontapi.client.BookFeignClient;
import com.tripleseven.frontapi.dto.coupon.CouponDetailsDTO;
import com.tripleseven.frontapi.dto.coupon.CouponPolicyRequestDTO;
import com.tripleseven.frontapi.dto.coupon.CouponPolicyResponseDTO;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class CouponController {

    private final BookFeignClient bookFeignClient;

//    @GetMapping("/api/coupon-history")
//    public String getCouponPolicyRegisterPage() {
//        return "/coupon-history";
//    }


    // 운영 환경 (디폴트 값 없이 헤더 강제)
    @GetMapping("/api/frontend/coupons/history")
    public String getCouponHistory(@RequestHeader("X-User") Long userId,
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

    // 테스트 환경 (defaultValue 제공)
    @GetMapping("/api/frontend/coupon-history-test")
    public String getCouponHistoryForTest(@RequestHeader(value = "X-User", defaultValue = "1") Long userId,
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

}

