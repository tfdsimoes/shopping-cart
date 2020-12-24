package xyz.lana.lanaserver.service;

import xyz.lana.lanaserver.dto.AddCartProductDTO;
import xyz.lana.lanaserver.dto.CartDTO;
import xyz.lana.lanaserver.dto.RemoveCartProductDTO;

import java.math.BigDecimal;

public interface CartService {

    /**
     * Create a new cart for the shopping
     *
     * @return cart created
     */
    CartDTO create();

    /**
     * Return the state of the cart
     *
     * @return State of the cart
     */
    CartDTO get();

    /**
     * Add product with a quantity to the cart
     *
     * @param addProductDTO {@link AddCartProductDTO} Product and quantity to be added to the cart
     * @return State of the cart
     */
    CartDTO addProduct(AddCartProductDTO addProductDTO);

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
     * @param removeCartProductDTO {@link RemoveCartProductDTO} Product and quantity to be removed form the cart
     * @return State of the cart
     */
    CartDTO deleteProduct(RemoveCartProductDTO removeCartProductDTO);
}
