package com.tripleseven.frontapi.controller.mypage.point;

import com.tripleseven.frontapi.dto.point.PageResponseDTO;
import com.tripleseven.frontapi.dto.point.UserPointHistoryDTO;
import com.tripleseven.frontapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class PointManageController {
    private final OrderService orderService;

    @GetMapping("/api/frontend/points/history")
    public String getPointHistoriesPage() {
        return "points-history";
    }

    @GetMapping("/api/frontend/points/history/data")
    public ResponseEntity<PageResponseDTO<UserPointHistoryDTO>> getPointHistories(
            @RequestHeader("X-USER") Long memberId,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            Pageable pageable
    ) {
        PageResponseDTO<UserPointHistoryDTO> pointHistories = orderService.getPointHistories(memberId, startDate, endDate, pageable);
        return ResponseEntity.ok(pointHistories);
    }


}
