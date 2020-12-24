package xyz.lana.lanaserver.service;

import xyz.lana.lanaserver.dto.CartDTO;

import java.math.BigDecimal;

public interface CartService {

    /**
     * Create a new cart for the shopping
     *
     * @return cart created
     */
    CartDTO create();

    /**
     * Add product with a quantity to the cart
     *
     * @param productId {@link String} Id of the product
     * @param quantity  {@link int} Quantity of the product to add to the cart
     * @return State of the cart
     */
    CartDTO addProduct(String productId, int quantity);

    /**
     * Get the total price of the cart
     *
     * @return Value of the cart
     */
    BigDecimal total();

    /**
     * Delete the cart
     *
     * @return If the delete was success
     */
    boolean delete();

    /**
     * Remove product from the cart
     *
     * @param productId {@link String} Id of the product
     * @param quantity  [@link int} Quantity of the product to remove
     * @return State of the cart
     */
    CartDTO deleteProduct(String productId, int quantity);
}
