package xyz.lana.lanaserver.service.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import xyz.lana.lanaserver.controller.dto.CartDTO;
import xyz.lana.lanaserver.entity.Cart;
import xyz.lana.lanaserver.entity.CartProduct;
import xyz.lana.lanaserver.entity.Product;
import xyz.lana.lanaserver.entity.Promotion;
import xyz.lana.lanaserver.mapper.CartMapper;
import xyz.lana.lanaserver.repository.ProductRepository;
import xyz.lana.lanaserver.repository.PromotionRepository;
import xyz.lana.lanaserver.service.CartService;

import java.math.BigDecimal;
import java.util.List;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class CartServiceImpl implements CartService {

    private final ProductRepository productRepository;

    private final PromotionRepository promotionRepository;

    private Cart cart = new Cart();

    public CartServiceImpl(ProductRepository productRepository, PromotionRepository promotionRepository) {
        this.productRepository = productRepository;
        this.promotionRepository = promotionRepository;
    }

    /**
     * Create a new cart for the shopping
     *
     * @return cart created
     */
    @Override
    public CartDTO create() {
        return CartMapper.INSTANCE.CartToCartDTO(new Cart());
    }

    /**
     * Add product with a quantity to the cart
     *
     * @param productId {@link String} Id of the product
     * @param quantity  {@link int} Quantity of the product to add to the cart
     * @return State of the cart
     */
    @Override
    public CartDTO addProduct(String productId, int quantity) {

        ValidationsCart.cartExist(cart);

        Product optionalProduct = productRepository.findProductById(productId).orElseThrow(() -> new RuntimeException("Product to add in the cart does not exist"));

        int pos = posProductsCart(productId);

        if (pos != -1) {
            // Add the new quantity of product to the already existing one
            int quantityOld = cart.getProducts().get(pos).getQuantity();
            int quantityNew = quantityOld + quantity;
            cart.getProducts().get(pos).setQuantity(quantityNew);
        } else {
            // Add product to the cart because does not exist in the cart
            CartProduct cartProduct = new CartProduct(productId, quantity);
            cart.getProducts().add(cartProduct);
        }

        calculateTotalCart();
        return CartMapper.INSTANCE.CartToCartDTO(cart);
    }

    /**
     * Get the total price of the cart
     *
     * @return Value of the cart
     */
    @Override
    public BigDecimal total() {
        ValidationsCart.cartExist(cart);
        return cart.getAmount();
    }

    /**
     * Delete the cart
     *
     * @return If the delete was success
     */
    @Override
    public boolean delete() {
        cart = null;
        return true;
    }

    /**
     * Remove product from the cart
     *
     * @param productId {@link String} Id of the product
     * @param quantity  [@link int} Quantity of the product to remove
     * @return State of the cart
     */
    @Override
    public CartDTO deleteProduct(String productId, int quantity) {
        ValidationsCart.cartExist(cart);

        int pos = posProductsCart(productId);

        if (pos != -1) {
            int quantityOld = cart.getProducts().get(pos).getQuantity();
            if (quantity < quantityOld) {
                // Update the quantity of the product in the cart
                int quantityNew = quantityOld - quantity;
                cart.getProducts().get(pos).setQuantity(quantityNew);
            } else if (quantity == quantityOld) {
                // Remove product from the cart
                cart.getProducts().remove(pos);
            } else {
                // Error the quantity to remove is bigger than the existent in the cart
                throw new RuntimeException("Quantity to delete is bigger then existent");
            }
        } else {
            throw new RuntimeException("Product does not exist in the cart");
        }

        calculateTotalCart();
        return CartMapper.INSTANCE.CartToCartDTO(cart);
    }

    /**
     * Get the position of the product in the cart products list
     *
     * @param productId {@link String} id of the product to search
     * @return -1 if the product does not exist in the list or the position in the list
     */
    private int posProductsCart(String productId) {
        int i = 0;

        for (CartProduct cartProduct : cart.getProducts()) {
            if (cartProduct.getProductId().equals(productId)) {
                return i;
            }
            i++;
        }

        return -1;
    }

    /**
     * Recalculate the value of the cart
     */
    private void calculateTotalCart() {
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CartProduct cartProduct : cart.getProducts()) {
            Product product = productRepository.findProductById(cartProduct.getProductId()).orElseThrow(() -> new RuntimeException("Product to add in the cart does not exist"));
            List<Promotion> promotions = promotionRepository.findPromotionsByProductId(cartProduct.getProductId());
            totalAmount = totalAmount.add(calculateTotalCartProduct(promotions, product,cartProduct.getQuantity()));
        }

        cart.setAmount(totalAmount);
    }

    /**
     * Calculate the value to pay for a quantity of a product with the promotions available
     *
     * @param promotions {@link List<Promotion>} List with the promotions available for the product
     * @param product    {@link Product} Product to calculate the value
     * @param quantity   {@link int} Quantity of the product
     * @return The total amount to pay for the
     */
    private BigDecimal calculateTotalCartProduct(List<Promotion> promotions, Product product, int quantity) {
        BigDecimal priceProduct = product.getPrice();
        BigDecimal amount = priceProduct.multiply(new BigDecimal(quantity));
        BigDecimal finalAmount = amount;

        for (Promotion promotion : promotions) {
            switch (promotion.getType()) {
                case twoXOne:
                    // Promotions X for 1
                    if(quantity > promotion.getMinQuantity()) {
                        int freeItems = quantity / promotion.getMinQuantity();
                        BigDecimal discountValue = priceProduct.multiply(BigDecimal.valueOf(freeItems));
                        finalAmount = finalAmount.subtract(discountValue);
                    }
                    break;
                case moreX:
                    // Promotions take X and receive Y discount
                    if(quantity > promotion.getMinQuantity()) {
                        // quantity * amount * discount
                        BigDecimal discountValue = BigDecimal.valueOf(quantity).multiply(amount.multiply(BigDecimal.valueOf(promotion.getDiscount() / 100)));
                        finalAmount = finalAmount.subtract(discountValue);
                    }
                    break;
            }
        }

        return finalAmount;
    }
}
