package com.tripleseven.frontapi.client;

import com.tripleseven.frontapi.dto.order.OrderDetailUpdateRequestDTO;
import com.tripleseven.frontapi.dto.order.OrderManageRequestDTO;
import com.tripleseven.frontapi.dto.order.OrderManageResponseDTO;
import com.tripleseven.frontapi.dto.order.OrderPayDetailDTO;
import com.tripleseven.frontapi.dto.policy.DeliveryPolicyDTO;
import com.tripleseven.frontapi.dto.policy.PointPolicyDTO;
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

    @GetMapping("/order-details/check-purchase")
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

    @PostMapping("/api/orders/order-details/return")
    void updateOrderDetails(
            @RequestHeader("X-USER") Long userId,
            @RequestBody OrderDetailUpdateRequestDTO orderDetailUpdateRequestDTO
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
}
