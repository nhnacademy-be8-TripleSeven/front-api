package com.tripleseven.frontapi.controller.cart;

import com.tripleseven.frontapi.client.MemberFeignClient;
import com.tripleseven.frontapi.dto.cart.CartDTO;
import com.tripleseven.frontapi.dto.policy.DefaultDeliveryPolicyDTO;
import com.tripleseven.frontapi.dto.policy.DeliveryPolicyType;
import com.tripleseven.frontapi.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/frontend/cart")
@RequiredArgsConstructor
public class CartController {

    private final MemberFeignClient memberFeignClient;
    private final OrderService orderService;

    @GetMapping
    public ModelAndView getCartPage(@RequestHeader(value = "X-USER", required = false) Long userId,
                                    @CookieValue("GUEST-ID") String guestId, ModelAndView modelAndView) {

        CartDTO cart = memberFeignClient.getCart(userId, guestId);
        return getCartModelAndView(modelAndView, cart);
    }

    @PutMapping("/books/{bookId}/quantity/{quantity}")
    public ModelAndView updateCartItemQuantity(@PathVariable long bookId,
                                               @PathVariable int quantity,
                                               @RequestHeader(value = "X-USER", required = false) Long userId,
                                               @CookieValue("GUEST-ID") String guestId, ModelAndView modelAndView) {

        CartDTO cart = memberFeignClient.updateCartItemQuantity(userId, bookId, guestId, quantity);
        return getCartModelAndView(modelAndView, cart);
    }

    @PostMapping("/books/{bookId}/quantity/{quantity}")
    public ModelAndView addCartItem(@PathVariable long bookId,
                                               @PathVariable int quantity,
                                               @RequestHeader(value = "X-USER", required = false) Long userId,
                                               @CookieValue("GUEST-ID") String guestId, ModelAndView modelAndView) {

        CartDTO cart = memberFeignClient.addCart(userId, guestId, bookId, quantity);
        return getCartModelAndView(modelAndView, cart);
    }

    @DeleteMapping("/books/{bookId}")
    public ModelAndView deleteCartItem(@PathVariable long bookId,
                                       @RequestHeader(value = "X-USER", required = false) Long userId,
                                       @CookieValue("GUEST-ID") String guestId, ModelAndView modelAndView) {
        CartDTO cart = memberFeignClient.deleteCartItem(userId, guestId, bookId);
        return getCartModelAndView(modelAndView, cart);
    }

    @DeleteMapping
    public ModelAndView clearCart(@RequestHeader(value = "X-USER", required = false) Long userId,
                                  @CookieValue("GUEST-ID") String guestId, ModelAndView modelAndView) {
        CartDTO cart = memberFeignClient.clearCart(userId, guestId);
        return getCartModelAndView(modelAndView, cart);
    }

    private  ModelAndView getCartModelAndView(ModelAndView modelAndView, CartDTO cart) {
        modelAndView.addObject("cart", cart);
        DefaultDeliveryPolicyDTO delivery = orderService.getDeliveryPrice(DeliveryPolicyType.DEFAULT);
        modelAndView.addObject("delivery", delivery);
        if (cart.getCartItems().isEmpty()) {
            modelAndView.setViewName("cart/cart-fail");
        } else {
            modelAndView.setViewName("cart/cart-success");
        }

        return modelAndView;
    }

}
