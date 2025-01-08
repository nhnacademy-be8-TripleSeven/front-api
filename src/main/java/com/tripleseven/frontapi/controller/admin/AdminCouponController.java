
package com.tripleseven.frontapi.controller.admin;

import com.tripleseven.frontapi.client.BookFeignClient;
import com.tripleseven.frontapi.dto.book.BookSearchDTO;
import com.tripleseven.frontapi.dto.category.CategorySearchDTO;
import com.tripleseven.frontapi.dto.coupon.CouponAssignResponseDTO;
import com.tripleseven.frontapi.dto.coupon.CouponCreationAndAssignRequestDTO;
import com.tripleseven.frontapi.dto.coupon.CouponPolicyRequestDTO;
import com.tripleseven.frontapi.dto.coupon.CouponPolicyResponseDTO;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/admin/frontend")
@RequiredArgsConstructor
public class AdminCouponController {

    private final BookFeignClient bookFeignClient;


    /**
     * 쿠폰 생성 페이지로 이동
     */
    @GetMapping("/coupons/create")
    public String showCouponCreatePage() {
        return "admin/coupon-create";
    }


    /**
     * 쿠폰 정책 생성 페이지로 이동
     */
    @GetMapping("/coupon-policies/register")
    public String showCouponPolicyRegisterPage(Model model) {
        // 새로운 쿠폰 정책 DTO를 모델에 추가하여 폼과 연동
        model.addAttribute("couponPolicy", new CouponPolicyRequestDTO());
        return "admin/coupon-policy-create";
    }

    /**
     * 쿠폰 정책 조회 페이지(검색 포함)
     */
    // 쿠폰 정책 조회 (검색 포함)
    @GetMapping("/coupon-policies/list")
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
        return "admin/check-coupon-policy";
    }






    // 쿠폰 정책 수정 데이터 조회
    @GetMapping("/coupon-policies/update/{id}")
    @ResponseBody
    public CouponPolicyResponseDTO getCouponPolicy(@PathVariable("id") Long id) {
        return bookFeignClient.getCouponPolicyById(id);
    }

    // 쿠폰 정책 수정 처리
    @PostMapping("/coupon-policies/update/{id}")
    @ResponseBody
    public String updateCouponPolicy(@PathVariable Long id, @RequestBody CouponPolicyRequestDTO request) {
        if (request.getCouponDiscountRate() != null && request.getCouponDiscountRate().compareTo(BigDecimal.ZERO) > 0) {
            request.setCouponDiscountRate(
                    request.getCouponDiscountRate().setScale(2, RoundingMode.HALF_UP)
            ); // 소수점 2자리 반올림
            request.setCouponDiscountAmount(null);
        } else if (request.getCouponDiscountAmount() != null && request.getCouponDiscountAmount() > 0) {
            request.setCouponDiscountRate(null);
        } else {
            throw new IllegalArgumentException("할인 금액 또는 할인율이 올바르지 않습니다.");
        }

        bookFeignClient.updateCouponPolicy(id, request);
        return "쿠폰 정책이 성공적으로 수정되었습니다.";
    }

    // 쿠폰 정책 삭제 처리
    @PostMapping("/coupon-policies/delete/{id}")
    @ResponseBody
    public String deleteCouponPolicy(@PathVariable("id") Long id) {
        bookFeignClient.deleteCouponPolicy(id);
        return "쿠폰 정책이 성공적으로 삭제되었습니다.";
    }

    //쿠폰 정책 생성 요청 처리
    @PostMapping("/coupon-policies/register")
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

    //쿠폰 생성 및 발급처리
    @PostMapping("/coupons/create")
    @ResponseBody
    public String createAndAssignCoupons(@RequestBody CouponCreationAndAssignRequestDTO request) {
        try {
            List<CouponAssignResponseDTO> responses = bookFeignClient.createAndAssignCoupons(request);
            return "쿠폰이 성공적으로 생성 및 발급되었습니다.";
        } catch (FeignException e) {
            return "쿠폰 생성/발급 중 오류가 발생했습니다: " + e.getMessage();
        }
    }

    // 도서 쿠폰 생성을 위한 도서검색
    @GetMapping("/coupons/book-search")
    @ResponseBody
    public List<BookSearchDTO> searchBooks(@RequestParam String query) {
        try {
            return bookFeignClient.searchBooksByName(query);
        } catch (FeignException.NotFound e) {
            return Collections.emptyList(); // 검색 결과 없음 처리
        }
    }

    // 카테고리 쿠폰 생성을 위한 카테고리 검색
    @GetMapping("/coupons/category-search")
    @ResponseBody
    public List<CategorySearchDTO> searchCategories(@RequestParam String query) {
        try {
            return bookFeignClient.searchCategoriesByName(query);
        } catch (FeignException.NotFound e) {
            return Collections.emptyList(); // 검색 결과 없음 처리
        }
    }

    @GetMapping("/coupon-policies/search")
    public String searchCouponPoliciesByName(
            @RequestParam(required = false) String query,
            Model model) {
        List<CouponPolicyResponseDTO> policies;
        try {
            policies = (query == null || query.isBlank())
                    ? Collections.emptyList()
                    : bookFeignClient.searchCouponPoliciesByName(query);
        } catch (Exception e) {
            policies = Collections.emptyList();
        }

        model.addAttribute("query", query); // 검색어 유지
        model.addAttribute("policies", policies); // 검색 결과를 모델에 추가
        return "/admin/coupon-policy-search"; // 검색 결과를 렌더링할 페이지
    }


}
