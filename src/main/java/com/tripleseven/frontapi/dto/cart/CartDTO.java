package com.tripleseven.frontapi.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@Getter
public class CartDTO implements Serializable {

    private final String Id;
    private List<CartItem> cartItems;

    @AllArgsConstructor
    @Getter
    public static class CartItem implements Serializable {
        private Long bookId;
        private String name;
        private String coverUrl;
        private int regularPrice;
        private int salePrice;
        private int quantity;
    }

}
