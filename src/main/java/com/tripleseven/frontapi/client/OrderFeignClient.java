package com.tripleseven.frontapi.client;

import com.tripleseven.frontapi.dto.order.*;
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

    @GetMapping("/api/order-details/check-purchase")
    boolean checkUserPurchase(@RequestParam("userId") Long userId,
                              @RequestParam("bookId") Long bookId);

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


    @GetMapping("/order-details/order-groups/{orderGroupId}")
    List<OrderDetailResponseDTO> getOrderDetailsByOrderGroupId(@PathVariable("orderGroupId") Long orderGroupId);
}
