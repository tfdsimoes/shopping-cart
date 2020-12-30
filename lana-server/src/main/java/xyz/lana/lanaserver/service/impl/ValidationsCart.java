package xyz.lana.lanaserver.service.impl;

import xyz.lana.lanaserver.entity.Cart;
import xyz.lana.lanaserver.exception.CartNotFound;

public class ValidationsCart {

    /**
     * Verify the cart is initialized
     *
     * @param cart {@link Cart} car to verify
     */
    public static void cartExist(Cart cart)  {
        if (cart == null) {
            throw new CartNotFound();
        }
    }
}
