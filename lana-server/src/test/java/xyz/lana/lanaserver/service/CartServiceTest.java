package xyz.lana.lanaserver.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import xyz.lana.lanaserver.dto.AddCartProductDTO;
import xyz.lana.lanaserver.dto.CartDTO;
import xyz.lana.lanaserver.dto.CartProductDTO;
import xyz.lana.lanaserver.dto.RemoveCartProductDTO;
import xyz.lana.lanaserver.entity.Product;
import xyz.lana.lanaserver.entity.Promotion;
import xyz.lana.lanaserver.entity.enums.PromotionType;
import xyz.lana.lanaserver.exception.CartNotFound;
import xyz.lana.lanaserver.exception.ProductCartNotFound;
import xyz.lana.lanaserver.exception.ProductNotFound;
import xyz.lana.lanaserver.exception.RemoveProductCart;
import xyz.lana.lanaserver.repository.ProductRepository;
import xyz.lana.lanaserver.repository.PromotionRepository;
import xyz.lana.lanaserver.service.impl.CartServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

/**
 * Class to check the functionality of {@link CartService}
 */
public class CartServiceTest {

    @Mock
    private static ProductRepository productRepository;

    @Mock
    private static PromotionRepository promotionRepository;

    private static CartService cartService;

    @BeforeAll
    public static void setUp() {
        productRepository = mock(ProductRepository.class);
        promotionRepository = mock(PromotionRepository.class);

        cartService = new CartServiceImpl(productRepository, promotionRepository);
    }

    /**
     * Check the creation of the cart
     */
    @Test
    public void createTest() {
        CartDTO cartDTO = cartService.create();

        assertTrue(cartDTO.getId().length() > 0, "Id should have some information");
        assertEquals(cartDTO.getAmount(), BigDecimal.ZERO, "Amount should be 0");
        assertEquals(cartDTO.getProducts().size(), 0, "Should not exist any products in the cart");

        cartService.delete();
    }

    /**
     * Check the add new product to the cart
     */
    @Test
    public void addProductTest() {
        Product product = buildDefaultProduct();
        AddCartProductDTO addCartProductDTO = buildDefaultCartProductDTO();

        Mockito.when(productRepository.findProductById(any())).thenReturn(Optional.of(product));
        Mockito.when(promotionRepository.findPromotionsByProductId(any())).thenReturn(new ArrayList<>());

        cartService.create();
        CartDTO cartDTO = cartService.addProduct(addCartProductDTO);

        assertTrue(cartDTO.getId().length() > 0, "Id should have information");
        assertEquals(cartDTO.getAmount(), BigDecimal.valueOf(10), "The value of the cart should be 10");
        assertEquals(cartDTO.getProducts().size(), 1, "Should exist one product in the cart");

        CartProductDTO cartProductDTO = cartDTO.getProducts().get(0);
        assertTrue(cartProductDTO.getId().length() > 0, "Id should have information");
        assertTrue(cartProductDTO.getProductId().length() > 0, "Should be an product id");
        assertEquals(cartProductDTO.getQuantity(), 10, "Quantity of the product should be 10");

        cartService.delete();
    }

    /**
     * Check the case when trying to add a product that does not exist in the system
     */
    @Test
    public void addProductNotFoundTest() {
        AddCartProductDTO addCartProductDTO = buildDefaultCartProductDTO();

        Mockito.when(productRepository.findProductById(any())).thenReturn(Optional.empty());

        cartService.create();

        ProductNotFound exception = assertThrows(
                ProductNotFound.class,
                () -> cartService.addProduct(addCartProductDTO),
                "Expected ProductNotFound"
        );

        assertEquals("Product to add in the cart does not exist", exception.getMessage(), "Exception message is not the expected one");

        cartService.delete();
    }

    /**
     * Check the case when adding a product to the cart and the cart is not initialized
     */
    @Test
    public void addProductNoCartTest() {
        Product product = buildDefaultProduct();
        AddCartProductDTO addCartProductDTO = buildDefaultCartProductDTO();

        Mockito.when(productRepository.findProductById(any())).thenReturn(Optional.of(product));
        Mockito.when(promotionRepository.findPromotionsByProductId(any())).thenReturn(new ArrayList<>());

        CartNotFound exception = assertThrows(
                CartNotFound.class,
                () -> cartService.addProduct(addCartProductDTO),
                "Expected CartNotFound"
        );

        assertEquals("Cart does not exist, initialize first", exception.getMessage(), "Exception message is not the expected");
    }

    /**
     * Check the total value of the cart
     */
    @Test
    public void totalTest() {
        Product product = buildDefaultProduct();
        AddCartProductDTO addCartProductDTO = buildDefaultCartProductDTO();

        Mockito.when(productRepository.findProductById(any())).thenReturn(Optional.of(product));
        Mockito.when(promotionRepository.findPromotionsByProductId(any())).thenReturn(new ArrayList<>());

        cartService.create();

        BigDecimal total = cartService.total();
        assertEquals(BigDecimal.ZERO, total, "The total value is not the expected one");

        cartService.addProduct(addCartProductDTO);

        total = cartService.total();
        assertEquals(BigDecimal.TEN, total, "The total value is not the expected one");

        cartService.delete();
    }

    /**
     * Check the delete of the cart from the system
     */
    @Test
    public void deleteTest() {
        cartService.create();

        boolean result = cartService.delete();

        assertTrue(result, "The response should be true");

        assertThrows(
                CartNotFound.class,
                () -> cartService.total(),
                "Was expecting error since cart is not initialized"
        );
    }

    /**
     * Check the remove of a product from the cart
     */
    @Test
    public void deleteProductTest() {

        initDeleteProductTests();

        RemoveCartProductDTO removeCartProductDTO = buildDefaultRemoveCartProductDTO();

        CartDTO cartDTO = cartService.deleteProduct(removeCartProductDTO);

        assertTrue(cartDTO.getId().length() > 0, "Id should be initialized");
        assertEquals(BigDecimal.ZERO, cartDTO.getAmount(), "Value should be 0");
        assertEquals(0, cartDTO.getProducts().size(), "No products should be in the list");

        cartService.delete();
    }

    /**
     * Check the case the product trying to remove from the cart does not exist in it
     */
    @Test
    public void deleteProductNotExistTest() {
        initDeleteProductTests();

        RemoveCartProductDTO removeCartProductDTO = buildDefaultRemoveCartProductDTO();
        removeCartProductDTO.setProductId("idDoesNotExist");

        ProductCartNotFound exception = assertThrows(
                ProductCartNotFound.class,
                () -> cartService.deleteProduct(removeCartProductDTO),
                "Was expecting an exception"
        );

        assertEquals("Product does not exist in the cart", exception.getMessage(), "Was expecting other exception message");

        cartService.delete();
    }

    /**
     * Check the case the quantity to remove from a product in the cart is bigger than in itf
     */
    @Test
    public void deleteProductWrongQuantity() {

        initDeleteProductTests();

        RemoveCartProductDTO removeCartProductDTO = buildDefaultRemoveCartProductDTO();
        removeCartProductDTO.setQuantity(100);

        RemoveProductCart exception = assertThrows(
                RemoveProductCart.class,
                () -> cartService.deleteProduct(removeCartProductDTO),
                "Was expecting an exception"
        );

        assertEquals("Quantity to delete is bigger then existent", exception.getMessage(), "Was expecting other exception message");

        cartService.delete();
    }

    /**
     * Check the appliance of the discount 2 for 1
     */
    @Test
    public void discount2x1Test() {
        Product product = buildDefaultProduct();
        Promotion promotion = buildPromotion(PromotionType.N_X_ONE);
        AddCartProductDTO addCartProductDTO = buildDefaultCartProductDTO();
        addCartProductDTO.setQuantity(1);

        Mockito.when(productRepository.findProductById(any())).thenReturn(Optional.of(product));
        Mockito.when(promotionRepository.findPromotionsByProductId(any())).thenReturn(Collections.singletonList(promotion));

        cartService.create();

        CartDTO cartDTO = cartService.addProduct(addCartProductDTO);

        assertEquals(BigDecimal.ONE, cartDTO.getAmount(), "The amount should be 1");
        assertEquals(1, cartDTO.getProducts().size(), "Should only exist 1 product");
        assertEquals(1, cartDTO.getProducts().get(0).getQuantity(), "Quantity should be 1");

        cartDTO = cartService.addProduct(addCartProductDTO);
        assertEquals(BigDecimal.ONE, cartDTO.getAmount(), "The amount should be 1");
        assertEquals(1, cartDTO.getProducts().size(), "Should exist 1 product");
        assertEquals(2, cartDTO.getProducts().get(0).getQuantity(), "Quantity should be 2");

        cartDTO = cartService.addProduct(addCartProductDTO);
        assertEquals(BigDecimal.valueOf(2), cartDTO.getAmount(), "The amount should be 2");
        assertEquals(1, cartDTO.getProducts().size(), "Should exist 1 product");
        assertEquals(3, cartDTO.getProducts().get(0).getQuantity(), "Quantity should be 3");

        cartDTO = cartService.addProduct(addCartProductDTO);
        assertEquals(BigDecimal.valueOf(2), cartDTO.getAmount(), "The amount should be 2");
        assertEquals(1, cartDTO.getProducts().size(), "Should exist 1 product");
        assertEquals(4, cartDTO.getProducts().get(0).getQuantity(), "Quantity should be 4");
    }

    /**
     * Check the appliance of the discount more than x equal to y discount
     */
    @Test
    public void discountMoreXTest() {
        Product product = buildDefaultProduct();
        Promotion promotion = buildPromotion(PromotionType.MORE_X);
        AddCartProductDTO addCartProductDTO = buildDefaultCartProductDTO();
        addCartProductDTO.setQuantity(1);

        Mockito.when(productRepository.findProductById(any())).thenReturn(Optional.of(product));
        Mockito.when(promotionRepository.findPromotionsByProductId(any())).thenReturn(Collections.singletonList(promotion));

        cartService.create();

        CartDTO cartDTO = cartService.addProduct(addCartProductDTO);
        assertEquals(BigDecimal.ONE, cartDTO.getAmount(), "The amount should be 1");
        assertEquals(1, cartDTO.getProducts().size(), "Should only exist 1 product");
        assertEquals(1, cartDTO.getProducts().get(0).getQuantity(), "Quantity should be 1");

        cartDTO = cartService.addProduct(addCartProductDTO);
        assertEquals(BigDecimal.valueOf(2), cartDTO.getAmount(), "The amount should be 2");
        assertEquals(1, cartDTO.getProducts().size(), "Should only exist 1 product");
        assertEquals(2, cartDTO.getProducts().get(0).getQuantity(), "Quantity should be 2");

        cartDTO = cartService.addProduct(addCartProductDTO);
        assertEquals(BigDecimal.valueOf(3 * 0.75), cartDTO.getAmount(), "The amount should be 2.25");
        assertEquals(1, cartDTO.getProducts().size(), "Should only exist 1 product");
        assertEquals(3, cartDTO.getProducts().get(0).getQuantity(), "Quantity should be 3");

        cartDTO = cartService.addProduct(addCartProductDTO);
        assertEquals(BigDecimal.valueOf(4 * 0.75).setScale(2), cartDTO.getAmount(), "The amount should be 3.00");
        assertEquals(1, cartDTO.getProducts().size(), "Should only exist 1 product");
        assertEquals(4, cartDTO.getProducts().get(0).getQuantity(), "Quantity should be 4");
    }

    /**
     * Build a default promotion to be used in the tests of the type desired
     *
     * @param type {@link PromotionType} Type of promotion to build
     * @return Object with the default values
     */
    private Promotion buildPromotion(PromotionType type) {
        Promotion promotion = new Promotion();
        promotion.setCode("promotion");
        promotion.setProductId("productId");
        promotion.setType(type);
        switch (type) {
            case N_X_ONE:
                promotion.setMinQuantity(2);
                promotion.setDiscount(100);
                break;
            case MORE_X:
                promotion.setMinQuantity(3);
                promotion.setDiscount(25);
                break;
        }

        return promotion;
    }

    /**
     * Initializer of the system from the tests of delete products from the cart
     */
    private void initDeleteProductTests() {
        Product product = buildDefaultProduct();
        AddCartProductDTO addCartProductDTO = buildDefaultCartProductDTO();

        Mockito.when(productRepository.findProductById(any())).thenReturn(Optional.of(product));
        Mockito.when(promotionRepository.findPromotionsByProductId(any())).thenReturn(new ArrayList<>());

        cartService.create();
        cartService.addProduct(addCartProductDTO);
    }

    /**
     * Build default {@link RemoveCartProductDTO} for tests
     *
     * @return Object with default values
     */
    private RemoveCartProductDTO buildDefaultRemoveCartProductDTO() {
        RemoveCartProductDTO removeCartProductDTO = new RemoveCartProductDTO();
        removeCartProductDTO.setProductId("productId");
        removeCartProductDTO.setQuantity(10);

        return removeCartProductDTO;
    }

    /**
     * Build a default {@link Product} for tests
     *
     * @return Product with default values
     */
    private Product buildDefaultProduct() {
        Product product = new Product();
        product.setCode("code");
        product.setPrice(BigDecimal.ONE);
        product.setName("name");

        return product;
    }

    /**
     * Build default {@link AddCartProductDTO} for tests
     *
     * @return Default object for the test
     */
    private AddCartProductDTO buildDefaultCartProductDTO() {
        AddCartProductDTO addCartProductDTO = new AddCartProductDTO();
        addCartProductDTO.setProductId("productId");
        addCartProductDTO.setQuantity(10);

        return addCartProductDTO;
    }
}