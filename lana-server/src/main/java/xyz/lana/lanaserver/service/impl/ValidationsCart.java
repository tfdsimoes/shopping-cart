package xyz.lana.lanaserver.service.impl;

import xyz.lana.lanaserver.entity.Cart;

public class ValidationsCart {

    /**
     * Verify the cart is initialized
     *
     * @param cart {@link Cart} car to verify
     */
    public static void cartExist(Cart cart)  {
        if (cart == null) {
            throw new RuntimeException("Cart does not exist, initialize first");
        }
    }
}
