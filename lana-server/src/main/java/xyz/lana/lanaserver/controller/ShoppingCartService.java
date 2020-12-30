package xyz.lana.lanaserver.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.lana.lanaserver.dto.AddCartProductDTO;
import xyz.lana.lanaserver.dto.CartDTO;
import xyz.lana.lanaserver.dto.RemoveCartProductDTO;
import xyz.lana.lanaserver.service.CartService;

import java.math.BigDecimal;

/**
 * Class with endpoints for cart resource
 */
@RestController
@RequestMapping(value = "/cart", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
public class ShoppingCartService {

    private CartService cartService;

    private ShoppingCartService(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping(value = "")
    public ResponseEntity<CartDTO> create() {
        return ResponseEntity.ok(cartService.create());
    }

    @GetMapping(value = "")
    public ResponseEntity<CartDTO> get() {
        return ResponseEntity.ok(cartService.get());
    }

    @DeleteMapping(value = "")
    public ResponseEntity<Boolean> delete() {
        return ResponseEntity.ok(cartService.delete());
    }

    @PostMapping(value = "/product")

    public ResponseEntity<CartDTO> addProduct(AddCartProductDTO addProductDTO) {
        if (addProductDTO.getQuantity() < 1) {
            ResponseEntity.badRequest();
        }

        return ResponseEntity.ok(cartService.addProduct(addProductDTO));
    }

    @GetMapping(value = "/total")
    public ResponseEntity<BigDecimal> getTotal() {
        return ResponseEntity.ok(cartService.total());
    }

    @DeleteMapping(value = "/product")
    public ResponseEntity<CartDTO> deleteProduct(RemoveCartProductDTO removeCartProductDTO) {
        if(removeCartProductDTO.getQuantity() < 1) {
            ResponseEntity.badRequest();
        }

        return ResponseEntity.ok(cartService.deleteProduct(removeCartProductDTO));
    }
}
