package com.tripleseven.frontapi.controller.admin;

import com.tripleseven.frontapi.dto.policy.*;
import com.tripleseven.frontapi.service.DeliveryPolicyService;
import com.tripleseven.frontapi.service.PointPolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/frontend")
public class AdminPolicyController {
    private final DeliveryPolicyService deliveryPolicyService;
    private final PointPolicyService pointPolicyService;

    @GetMapping("/policies/delivery")
    public String getDeliveryPolicies(
            Model model) {
        List<DeliveryPolicyDTO> dtos = deliveryPolicyService.getAllDeliveryPolicies();
        model.addAttribute("deliveryPolicies", dtos);
        return "admin/delivery-policy";
    }

    @GetMapping("/policies/delivery/create")
    public String getDeliveryPolicyCreatePage() {
        return "admin/delivery-policy-create";
    }

    @GetMapping("/policies/point")
    public String getPointPolicies(
            Model model) {
        List<PointPolicyDTO> pointPolicies = pointPolicyService.getPointPolicies();
        model.addAttribute("points", pointPolicies);
        return "admin/point-policy";
    }

    @GetMapping("/policies/point/create")
    public String getPointPolicyCreatePage() {
        return "admin/point-policy-create";
    }
}
