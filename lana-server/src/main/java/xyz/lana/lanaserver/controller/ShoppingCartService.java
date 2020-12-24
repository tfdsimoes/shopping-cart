package xyz.lana.lanaserver.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.lana.lanaserver.service.CartService;

import java.math.BigDecimal;

/**
 * Class with endpoints for cart resource
 */
@RestController
@RequestMapping(value = "/cart", produces = MediaType.APPLICATION_JSON_VALUE)
public class ShoppingCartService {

    private CartService cartService;

    private ShoppingCartService(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping(value = "")
    public ResponseEntity<Void> create() {
        cartService.create();
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/product")
    public ResponseEntity<String> addProduct(String idProduct) {
        return (ResponseEntity<String>) ResponseEntity.notFound();
    }

    @GetMapping(value = "/{idCart}/total")
    public ResponseEntity<BigDecimal> getTotal(@PathVariable("idCart") String idCart) {
        return (ResponseEntity<BigDecimal>) ResponseEntity.notFound();
    }

    @DeleteMapping(value = "/{idCart}")
    public ResponseEntity<Boolean> delete(@PathVariable("idCart") String idCart) {
        return (ResponseEntity<Boolean>) ResponseEntity.notFound();
    }

    @DeleteMapping(value = "/{idCart}/product/{idProduct}")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable("idCart") String idCart, @PathVariable("idProduct") String idProduct) {
        return (ResponseEntity<Boolean>) ResponseEntity.notFound();
    }
}
