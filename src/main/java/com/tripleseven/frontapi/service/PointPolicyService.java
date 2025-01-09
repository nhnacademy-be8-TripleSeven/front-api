package com.tripleseven.frontapi.service;

import com.tripleseven.frontapi.client.OrderFeignClient;
import com.tripleseven.frontapi.dto.policy.PointPolicyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PointPolicyService {
    private final OrderFeignClient orderFeignClient;

    public List<PointPolicyDTO> getPointPolicies(){
        return orderFeignClient.getAllPointPolicies();
    }
}
