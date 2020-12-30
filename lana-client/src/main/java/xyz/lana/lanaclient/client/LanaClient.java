package xyz.lana.lanaclient.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.lana.lanaclient.config.FeignServiceConfiguration;
import xyz.lana.lanaclient.dto.AddCartProductDTO;
import xyz.lana.lanaclient.dto.CartDTO;
import xyz.lana.lanaclient.dto.RemoveCartProductDTO;

import java.math.BigDecimal;

@FeignClient(name = "lana-client", url = "${lana-server.url}", configuration = FeignServiceConfiguration.class)
@RequestMapping(value = "/cart", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public interface LanaClient {

    @PostMapping(value = "")
    ResponseEntity<CartDTO> create();

    @GetMapping
    ResponseEntity<CartDTO> get();

    @DeleteMapping
    ResponseEntity<Boolean> delete();

    @PostMapping(value = "/product")
    ResponseEntity<CartDTO> addProduct(AddCartProductDTO addCartProductDTO);

    @GetMapping(value = "/total")
    ResponseEntity<BigDecimal> getTotal();

    @DeleteMapping(value = "/product")
    ResponseEntity<CartDTO> deleteProduct(RemoveCartProductDTO removeCartProductDTO);
}
