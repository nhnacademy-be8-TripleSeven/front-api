package com.tripleseven.frontapi.service;

import com.tripleseven.frontapi.client.OrderFeignClient;
import com.tripleseven.frontapi.dto.policy.DeliveryPolicyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryPolicyService {
    private final OrderFeignClient orderFeignClient;

    public List<DeliveryPolicyDTO> getAllDeliveryPolicies(){
        return orderFeignClient.getAllDeliveryPolicies();
    }
}
