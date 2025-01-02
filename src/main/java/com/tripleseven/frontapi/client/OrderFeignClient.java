package com.tripleseven.frontapi.client;

import com.tripleseven.frontapi.dto.order.OrderDetailDTO;
import com.tripleseven.frontapi.dto.order.OrderManageRequestDTO;
import com.tripleseven.frontapi.dto.order.OrderManageResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "order-api")
public interface OrderFeignClient {
    @PostMapping("/api/orders/order-groups/period")
    Page<OrderManageResponseDTO> getOrderList(@RequestBody OrderManageRequestDTO orderManageRequestDTO,
                                              @RequestHeader("X-USER") Long userId,
                                              Pageable pageable);

    @GetMapping("/api/order-details/check-purchase")
    boolean checkUserPurchase(@RequestParam("userId") Long userId,
                              @RequestParam("bookId") Long bookId);

    @GetMapping("/api/user/point-histories/point")
    Integer getTotalPoint(@RequestHeader("X-USER") Long userId);

    @GetMapping("/api/user/point-histories/details")
    OrderDetailDTO getOrderDetails(@RequestHeader("X-USER") Long userId,
                                   Long orderId);
}
