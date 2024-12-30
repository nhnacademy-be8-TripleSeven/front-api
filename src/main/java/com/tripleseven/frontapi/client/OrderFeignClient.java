package com.tripleseven.frontapi.client;

import com.tripleseven.frontapi.dto.order.OrderManageRequestDTO;
import com.tripleseven.frontapi.dto.order.OrderManageResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "gateway", contextId = "order-api")
public interface OrderFeignClient {
    @GetMapping("/order-groups/period")
    List<OrderManageResponseDTO> getOrderList(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sort") String sort,
            @RequestBody OrderManageRequestDTO orderManageRequestDTO
    );
}
