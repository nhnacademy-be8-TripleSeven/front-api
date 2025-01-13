package com.tripleseven.frontapi.client;

import com.tripleseven.frontapi.dto.MemberDTO;
import com.tripleseven.frontapi.dto.cart.CartDTO;
import com.tripleseven.frontapi.dto.member.MemberAccountDto;
import com.tripleseven.frontapi.dto.oauth2.payco.PaycoMemberDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "member-api")
public interface MemberFeignClient {

    @PostMapping("/members/oauth2/payco")
    MemberAccountDto savePaycoMember(@RequestBody PaycoMemberDTO.Data.PaycoMember paycoMember);

    @GetMapping("/admin/members")
    Page<MemberDTO> getMembers(@RequestParam(required = false) String name,
                               @RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size,
                               @RequestParam(required = false) String sort,
                               @RequestParam(defaultValue = "ASC") String sortOrder);


    @PostMapping("/members/unlock/account/verify")
    ResponseEntity<Void> verifyAccountActiveCode(@RequestParam String email, @RequestParam String code);

    @GetMapping("/cart")
    CartDTO getCart(@RequestHeader(value = "X-USER", required = false) Long userId,
                    @CookieValue("GUEST-ID") String guestId);

    @PostMapping("/cart")
    CartDTO addCart(@RequestHeader(value = "X-USER", required = false) Long userId,
                           @CookieValue("GUEST-ID") String guestId,
                           @RequestParam Long bookId,
                           @RequestParam int quantity);

    @PutMapping("/cart/book/quantity")
    CartDTO updateCartItemQuantity(@RequestHeader(value = "X-USER", required = false) Long userId,
                                   @RequestParam Long bookId,
                                   @CookieValue("GUEST-ID") String guestId,
                                   @RequestParam int quantity);

    @DeleteMapping("/cart/book")
    CartDTO deleteCartItem(@RequestHeader(value = "X-USER", required = false) Long userId,
                           @CookieValue("GUEST-ID") String guestId,
                           @RequestParam Long bookId);

    @DeleteMapping("/cart")
    CartDTO clearCart(@RequestHeader(value = "X-USER", required = false) Long userId,
                      @CookieValue("GUEST-ID") String guestId);



    @GetMapping("/api/members/info")
    MemberDTO getMemberInfo(@RequestHeader Long userId);
}
