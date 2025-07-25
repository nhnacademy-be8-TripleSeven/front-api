package com.tripleseven.frontapi.client;

import com.tripleseven.frontapi.dto.order.*;
import com.tripleseven.frontapi.dto.pay.PayInfoRequestDTO;
import com.tripleseven.frontapi.dto.pay.PayInfoResponseDTO;
import com.tripleseven.frontapi.dto.point.PointHistoryPageResponseDTO;
import com.tripleseven.frontapi.dto.point.UserPointHistoryDTO;
import com.tripleseven.frontapi.dto.policy.*;
import org.json.simple.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "order-api")
public interface OrderFeignClient {
    @PostMapping("/api/orders/order-groups/period")
    Page<OrderManageResponseDTO> getOrderList(
            @RequestBody OrderManageRequestDTO orderManageRequestDTO,
            @RequestHeader("X-USER") Long userId,
            Pageable pageable);

    @GetMapping("/orders/order-details/check-purchase")
    boolean checkUserPurchase(@RequestParam("bookId") Long bookId,
                              @RequestParam("userId") Long userId);

    @GetMapping("/api/user/point-histories/point")
    Integer getTotalPoint(
            @RequestHeader("X-USER") Long userId
    );

    @GetMapping("/api/orders/order-groups/{orderId}")
    OrderPayDetailDTO getOrderDetails(
            @RequestHeader("X-USER") Long userId,
            @PathVariable("orderId") Long orderId
    );


    @GetMapping("/orders/order-groups")
    List<OrderGroupResponseDTO> getGuestOrderGroups(@RequestParam("phone") String phone);


    @GetMapping("/orders/order-details/{order-group-id}")
    List<OrderDetailResponseDTO> getOrderDetailsByOrderGroupId(@PathVariable("order-group-id") Long orderGroupId);

    @PostMapping("/api/orders/order-details/return")
    void updateOrderDetails(
            @RequestHeader("X-USER") Long userId,
            @RequestBody OrderDetailUpdateRequestDTO orderDetailUpdateRequestDTO
    );

    @GetMapping("/api/orders/point-histories")
    PointHistoryPageResponseDTO<UserPointHistoryDTO> getUserPointHistories(
            @RequestHeader("X-USER") Long memberId,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            Pageable pageable
    );

    @GetMapping("/admin/orders/delivery-policies")
    List<DeliveryPolicyDTO> getAllDeliveryPolicies();

    @GetMapping("/admin/orders/point-policies")
    List<PointPolicyDTO> getAllPointPolicies();

    @PostMapping("/admin/orders/order-groups/period")
    Page<OrderManageResponseDTO> getAdminOrderList(
            @RequestBody OrderManageRequestDTO orderManageRequestDTO,
            Pageable pageable);

    @GetMapping("/admin/orders/order-groups/{orderId}")
    OrderPayDetailDTO getAdminOrderDetails(
            @PathVariable("orderId") Long orderId
    );

    @GetMapping("/admin/orders/default-policies")
    DefaultPolicyDTO getDefaultPolicies();


    @PostMapping("/payments/order")
    PayInfoResponseDTO getPayInfo(
            @RequestHeader("X-USER") Long userId,
            @CookieValue("GUEST-ID") String guestId,
            @RequestBody PayInfoRequestDTO payInfoRequestDTO
    );

    @PostMapping("/confirm/payment")
    JSONObject confirmPayment(@RequestHeader("X-USER") Long userId,
                              @CookieValue("GUEST-ID") String guestId,
                              String jsonBody);

    @GetMapping("/orders/default-policy/delivery")
    DefaultDeliveryPolicyDTO getDefaultDeliveryPolicy(@RequestParam DeliveryPolicyType type);

    @GetMapping("/orders/wrappings")
    List<WrappingResponseDTO> getAllWrappings();

    @GetMapping("/orders/order-groups/{id}")
    OrderGroupResponseDTO getOrderGroupById(@PathVariable("id") Long id);

    @GetMapping("/orders/delivery-info/{id}")
    DeliveryInfoResponseDTO getDeliveryInfoById(@PathVariable("id") Long id);

    @GetMapping("/orders/pay/{orderId}")
    Long getPayPrice(@PathVariable Long orderId);

    @GetMapping("/orders/wrappings/{wrappingId}")
    WrappingResponseDTO getWrappingById(@PathVariable("wrappingId") Long wrappingId);
}
