package com.tripleseven.frontapi.client;

import com.tripleseven.frontapi.dto.order.OrderDetailUpdateRequestDTO;
import com.tripleseven.frontapi.dto.order.OrderManageRequestDTO;
import com.tripleseven.frontapi.dto.order.OrderManageResponseDTO;
import com.tripleseven.frontapi.dto.order.OrderPayDetailDTO;
import com.tripleseven.frontapi.dto.point.PointHistoryPageResponseDTO;
import com.tripleseven.frontapi.dto.point.UserPointHistoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/api/user/point-histories")
    PointHistoryPageResponseDTO<UserPointHistoryDTO> getUserPointHistories(
            @RequestHeader("X-USER") Long memberId,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            Pageable pageable
    );
}
