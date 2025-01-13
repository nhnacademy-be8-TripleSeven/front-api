package com.tripleseven.frontapi.service;

import com.tripleseven.frontapi.client.OrderFeignClient;
import com.tripleseven.frontapi.dto.FilterCriteriaDTO;
import com.tripleseven.frontapi.dto.book.BookOrderDetailResponse;
import com.tripleseven.frontapi.dto.order.*;
import com.tripleseven.frontapi.dto.point.PointHistoryPageResponseDTO;
import com.tripleseven.frontapi.dto.point.UserPointHistoryDTO;
import com.tripleseven.frontapi.dto.pay.PayInfoRequestDTO;
import com.tripleseven.frontapi.dto.pay.PayInfoResponseDTO;
import com.tripleseven.frontapi.dto.policy.DefaultDeliveryPolicyDTO;
import com.tripleseven.frontapi.dto.policy.DeliveryPolicyType;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderFeignClient orderFeignClient;
    private final BookService bookService;

    public Page<OrderManageResponseDTO> getOrderHistories(FilterCriteriaDTO filterCriteriaDTO, Long userId, Pageable pageable) {
        OrderStatus orderStatus = filterCriteriaDTO.getOrderStatus();
        if (orderStatus.equals(OrderStatus.ALL)) {
            orderStatus = null;
        }
        OrderManageRequestDTO requestDTO = new OrderManageRequestDTO(
                filterCriteriaDTO.getStartDate(),
                filterCriteriaDTO.getEndDate(),
                orderStatus
        );
        return orderFeignClient.getOrderList(requestDTO, userId, pageable);
    }

    public OrderPayDetailDTO getOrderHistory(Long userId, Long orderId) {
        return orderFeignClient.getOrderDetails(userId, orderId);
    }

    public int getPoints(Long userId) {
        return orderFeignClient.getTotalPoint(userId);
    }

    public void updateOrderHistories(Long userId, List<Long> ids, OrderStatus orderStatus){
        OrderDetailUpdateRequestDTO orderDetailUpdateRequest = new OrderDetailUpdateRequestDTO(ids, orderStatus);
        orderFeignClient.updateOrderDetails(userId, orderDetailUpdateRequest);
    }

    public Page<OrderManageResponseDTO> getAdminOrderHistories(FilterCriteriaDTO filterCriteriaDTO, Pageable pageable) {
        OrderStatus orderStatus = filterCriteriaDTO.getOrderStatus();
        if (orderStatus.equals(OrderStatus.ALL)) {
            orderStatus = null;
        }
        OrderManageRequestDTO requestDTO = new OrderManageRequestDTO(
                filterCriteriaDTO.getStartDate(),
                filterCriteriaDTO.getEndDate(),
                orderStatus
        );
        return orderFeignClient.getAdminOrderList(requestDTO, pageable);
    }

    public OrderPayDetailDTO getAdminOrderHistory(Long orderId) {
        return orderFeignClient.getAdminOrderDetails(orderId);
    }

    public PointHistoryPageResponseDTO<UserPointHistoryDTO> getPointHistories(Long memberId, String startDate, String endDate, Pageable pageable) {
        return orderFeignClient.getUserPointHistories(memberId, startDate, endDate, pageable);
    }



    public ProductDTO getProductInfoByDirect(Long bookId, int quantity){
        BookOrderDetailResponse bookDetailDTO = bookService.getBookOrderDetail(bookId);
        ProductDTO productDTO = new ProductDTO();
        productDTO.ofCreate(bookDetailDTO,quantity);
        return productDTO;
    }
    public List<ProductDTO> getProductInfoByCart(){
        return null;
    }

    public PayInfoResponseDTO getPayInfo(Long userId, String guestId,PayInfoRequestDTO requestDTO) {
        return orderFeignClient.getPayInfo(userId,guestId,requestDTO);
    }

    public JSONObject getPayment(String jsonBody){
        return orderFeignClient.confirmPayment(jsonBody);
    }

    public int getDeliveryPrice(DeliveryPolicyType type){
        DefaultDeliveryPolicyDTO defaultDeliveryPolicyDTO = orderFeignClient.getDefaultDeliveryPolicy(type);
        return defaultDeliveryPolicyDTO.getPrice();
    }
}
