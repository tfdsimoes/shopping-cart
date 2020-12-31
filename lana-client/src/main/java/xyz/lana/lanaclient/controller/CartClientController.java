package xyz.lana.lanaclient.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.lana.lanaclient.client.LanaClient;
import xyz.lana.lanaclient.dto.AddCartProductDTO;
import xyz.lana.lanaclient.dto.CartDTO;
import xyz.lana.lanaclient.dto.RemoveCartProductDTO;

import java.math.BigDecimal;

@RestController
@RequestMapping(value = "/cart", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class CartClientController {

    private final LanaClient lanaClient;

    public CartClientController(LanaClient lanaClient) {
        this.lanaClient = lanaClient;
    }

    @PostMapping(value = "")
    public ResponseEntity<CartDTO> create() {
        return lanaClient.create();
    }

    @GetMapping(value = "")
    public ResponseEntity<CartDTO> get() {
        return lanaClient.get();
    }

    @DeleteMapping(value = "")
    public ResponseEntity<Boolean> delete() {
        return lanaClient.delete();
    }

    @PostMapping(value = "/product")
    public ResponseEntity<CartDTO> addProduct(AddCartProductDTO addCartProductDTO) {
        return lanaClient.addProduct(addCartProductDTO);
    }

    @GetMapping(value = "/total")
    public ResponseEntity<BigDecimal> getTotal() {
        return lanaClient.getTotal();
    }

    @DeleteMapping(value = "/product")
    public ResponseEntity<CartDTO> deleteProduct(RemoveCartProductDTO removeCartProductDTO) {
        return lanaClient.deleteProduct(removeCartProductDTO);
    }
}
