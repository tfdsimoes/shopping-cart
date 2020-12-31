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
    public ResponseEntity<CartDTO> createCart(@RequestHeader(value = "Cookie", required = true) String session) {
        return lanaClient.create(session);
    }

    @GetMapping(value = "")
    public ResponseEntity<CartDTO> get(@RequestHeader(value = "Cookie", required = true) String session) {
        return lanaClient.get(session);
    }

    @DeleteMapping(value = "")
    public ResponseEntity<Boolean> delete(@RequestHeader(value = "Cookie", required = true) String session) {
        return lanaClient.delete(session);
    }

    @PostMapping(value = "/product")
    public ResponseEntity<CartDTO> addProduct(
            @RequestHeader(value = "Cookie", required = true) String session,
            @RequestBody AddCartProductDTO addCartProductDTO)
    {
        return lanaClient.addProduct(session, addCartProductDTO);
    }

    @GetMapping(value = "/total")
    public ResponseEntity<BigDecimal> getTotal(@RequestHeader(value = "Cookie", required = true) String session) {
        return lanaClient.getTotal(session);
    }

    @DeleteMapping(value = "/product")
    public ResponseEntity<CartDTO> deleteProduct(
            @RequestHeader(value = "Cookie", required = true) String session,
            @RequestBody RemoveCartProductDTO removeCartProductDTO)
    {
        return lanaClient.deleteProduct(session, removeCartProductDTO);
    }
}
