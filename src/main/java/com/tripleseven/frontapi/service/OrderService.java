package com.tripleseven.frontapi.service;

import com.tripleseven.frontapi.client.OrderFeignClient;
import com.tripleseven.frontapi.dto.FilterCriteriaDTO;
import com.tripleseven.frontapi.dto.book.BookDetailViewDTO;
import com.tripleseven.frontapi.dto.order.*;
import com.tripleseven.frontapi.dto.pay.PayInfoRequestDTO;
import com.tripleseven.frontapi.dto.pay.PayInfoResponseDTO;
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
        Status status = filterCriteriaDTO.getStatus();
        if (status.equals(Status.ALL)) {
            status = null;
        }
        OrderManageRequestDTO requestDTO = new OrderManageRequestDTO(
                filterCriteriaDTO.getStartDate(),
                filterCriteriaDTO.getEndDate(),
                status
        );
        return orderFeignClient.getOrderList(requestDTO, userId, pageable);
    }

    public OrderPayDetailDTO getOrderHistory(Long userId, Long orderId) {
        return orderFeignClient.getOrderDetails(userId, orderId);
    }

    public int getPoints(Long userId) {
        return orderFeignClient.getTotalPoint(userId);
    }

    public ProductDTO getProductInfoByDirect(Long bookId, int quantity){
        BookDetailViewDTO bookDetailDTO = bookService.getBookDetail(bookId);
        ProductDTO productDTO = new ProductDTO();
        productDTO.ofCreate(bookDetailDTO,quantity);
        return productDTO;
    }
    public List<ProductDTO> getProductInfoByCart(){
        return null;
    }

    public PayInfoResponseDTO getPayInfo(Long userId, Long guestId,PayInfoRequestDTO requestDTO) {
        return orderFeignClient.getPayInfo(userId,guestId,requestDTO);
    }

    public JSONObject getPayment(String jsonBody){
        return orderFeignClient.confirmPayment(jsonBody);
    }
}
