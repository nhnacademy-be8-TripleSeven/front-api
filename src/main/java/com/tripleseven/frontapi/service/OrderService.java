package com.tripleseven.frontapi.service;

import com.tripleseven.frontapi.client.MemberFeignClient;
import com.tripleseven.frontapi.client.OrderFeignClient;
import com.tripleseven.frontapi.dto.FilterCriteriaDTO;
import com.tripleseven.frontapi.dto.book.BookDetailViewDTO;
import com.tripleseven.frontapi.dto.book.BookOrderDetailResponse;
import com.tripleseven.frontapi.dto.book.BookOrderRequestDTO;
import com.tripleseven.frontapi.dto.cart.CartDTO;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderFeignClient orderFeignClient;
    private final BookService bookService;
    private final MemberFeignClient memberFeignClient;

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

    public void updateOrderHistories(Long userId, List<Long> ids, OrderStatus orderStatus) {
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


    public List<ProductDTO> getProductsByType(String type, Long bookId, int quantity, Long userId, String guestId) {
        if ("direct".equals(type)) {
            return List.of(getProductInfoByDirect(bookId, quantity));
        } else if ("cart".equals(type)) {
            return getProductInfoByCart(userId, guestId);
        } else {
            throw new IllegalArgumentException("Unsupported type: " + type);
        }
    }

    public ProductDTO getProductInfoByDirect(Long bookId, int quantity) {
        List<BookOrderRequestDTO> requestDTO = List.of(new BookOrderRequestDTO(bookId, quantity));
        List<BookOrderDetailResponse> bookDetailDTO = bookService.getBookOrderDetail(requestDTO);
        ProductDTO productDTO = new ProductDTO();
        productDTO.ofCreate(bookDetailDTO.getFirst(), quantity);
        return productDTO;
    }

    public List<ProductDTO> getProductInfoByCart(Long userId, String guestId) {
        CartDTO cartDTO = memberFeignClient.getCart(userId, guestId);
        List<CartDTO.CartItem> cartItems = cartDTO.getCartItems();
        List<BookOrderRequestDTO> requestDTOS = new ArrayList<>();
        for (CartDTO.CartItem cartItem : cartItems) {
            requestDTOS.add(new BookOrderRequestDTO(cartItem.getBookId(), cartItem.getQuantity()));
        }
        List<BookOrderDetailResponse> bookDetailDTO = bookService.getBookOrderDetail(requestDTOS);
        List<ProductDTO> productDTOS = new ArrayList<>();
        for (int i = 0; i < bookDetailDTO.size(); i++) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.ofCreate(bookDetailDTO.get(i), cartItems.get(i).getQuantity());
            productDTOS.add(productDTO);
        }
        return productDTOS;
    }

    public PayInfoResponseDTO getPayInfo(Long userId, String guestId, PayInfoRequestDTO requestDTO) {
        return orderFeignClient.getPayInfo(userId, guestId, requestDTO);
    }

    public JSONObject getPayment(Long userId, String guestId, String jsonBody) {
        return orderFeignClient.confirmPayment(userId, guestId, jsonBody);
    }

    public DefaultDeliveryPolicyDTO getDeliveryPrice(DeliveryPolicyType type) {
        return orderFeignClient.getDefaultDeliveryPolicy(type);
    }

    public List<WrappingResponseDTO> getAllWrappings() {
        return orderFeignClient.getAllWrappings();
    }

    public OrderGroupResponseDTO getOrderGroup(Long orderId) {
        return orderFeignClient.getOrderGroupById(orderId);
    }

    public List<OrderDetailResponseDTO> getOrderDetailsByOrderGroupId(Long orderGroupId) {
        return orderFeignClient.getOrderDetailsByOrderGroupId(orderGroupId);
    }

    public TotalOrderCompleteDTO getOrderCompleteDTO(OrderGroupResponseDTO orderGroup) {
        List<OrderDetailResponseDTO> orderDetails = orderFeignClient.getOrderDetailsByOrderGroupId(orderGroup.getId()); //상세 주문 조회
        List<BookDetailViewDTO> bookDetailViewDTOS = new ArrayList<>();

        for (OrderDetailResponseDTO orderDetail : orderDetails) {
            bookDetailViewDTOS.add(bookService.getBookDetail(orderDetail.getBookId()));
        }
        DeliveryInfoResponseDTO deliveryInfoResponseDTO = orderFeignClient.getDeliveryInfoById(orderGroup.getId());
        List<OrderCompleteDTO> orderCompleteDTOS = new ArrayList<>();
        for (int i = 0; i < orderDetails.size(); i++) {
            orderCompleteDTOS.add(new OrderCompleteDTO(orderDetails.get(i), bookDetailViewDTOS.get(i), deliveryInfoResponseDTO));
        }
        if (Objects.isNull(orderGroup.getWrappingId())) {
            return new TotalOrderCompleteDTO(orderCompleteDTOS, deliveryInfoResponseDTO);
        }
        WrappingResponseDTO wrappingResponseDTO = orderFeignClient.getWrappingById(orderGroup.getWrappingId());

        return new TotalOrderCompleteDTO(orderCompleteDTOS, wrappingResponseDTO, deliveryInfoResponseDTO);

    }

    public Long getPayPrice(Long orderId) {
        return orderFeignClient.getPayPrice(orderId);
    }

    public OrderCalculationResult calculateOrder(List<ProductDTO> products, Long userId) {
        // 총 상품 금액 및 할인 금액 계산
        int productAmount = products.stream()
                .mapToInt(p -> p.getPrice() * p.getQuantity())
                .sum();

        int discountAmount = products.stream()
                .mapToInt(p -> (p.getPrice() - p.getDiscountedPrice()) * p.getQuantity())
                .sum();

        int finalAmount = productAmount - discountAmount;

        // 배송 정책 조회
        DefaultDeliveryPolicyDTO deliveryPolicy = getDeliveryPrice(DeliveryPolicyType.DEFAULT);
        int deliveryPrice = deliveryPolicy.getPrice();
        int deliveryMinPrice = deliveryPolicy.getMinPrice();
        int additionalAmount = finalAmount < deliveryMinPrice ? deliveryPrice : 0;

        // 회원 포인트 및 쿠폰 조회
        int availablePoint = userId != null ? getPoints(userId) : 0;


        return new OrderCalculationResult(
                productAmount, discountAmount, finalAmount,
                deliveryPrice, deliveryMinPrice, additionalAmount,
                availablePoint
        );
    }


}
