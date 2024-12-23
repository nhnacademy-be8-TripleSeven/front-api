package com.tripleseven.frontapi.client;

import com.tripleseven.frontapi.domain.Member;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "gateway-1")
public interface TestApiFeignClient {
    @GetMapping("/members")
    Member getMember(@RequestParam Long id);
}
