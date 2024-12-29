package com.tripleseven.frontapi.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "order-api")
public interface OrderFeignClient {

    @GetMapping("/order-details/check-purchase")
    boolean checkUserPurchase(@RequestParam("userId") Long userId, @RequestParam("bookId") Long bookId);
}
