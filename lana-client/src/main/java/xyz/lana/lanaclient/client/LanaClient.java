package xyz.lana.lanaclient.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import xyz.lana.lanaclient.config.FeignServiceConfiguration;
import xyz.lana.lanaclient.dto.AddCartProductDTO;
import xyz.lana.lanaclient.dto.CartDTO;
import xyz.lana.lanaclient.dto.RemoveCartProductDTO;

import java.math.BigDecimal;

@FeignClient(name = "lana-client", url = "${lana-server.url}", configuration = FeignServiceConfiguration.class)
public interface LanaClient {

    @PostMapping(value = "/cart", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CartDTO> create(@RequestHeader(value = "Cookie", required = true) String session);

    @GetMapping(value = "/cart", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CartDTO> get(@RequestHeader(value = "Cookie", required = true) String session);

    @DeleteMapping(value = "/cart", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Boolean> delete(@RequestHeader(value = "Cookie", required = true) String session);

    @PostMapping(value = "/cart/product", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CartDTO> addProduct(@RequestHeader(value = "Cookie", required = true) String session, AddCartProductDTO addCartProductDTO);

    @GetMapping(value = "/cart/total", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BigDecimal> getTotal(@RequestHeader(value = "Cookie", required = true) String session);

    @DeleteMapping(value = "/cart/product", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CartDTO> deleteProduct(@RequestHeader(value = "Cookie", required = true) String session, RemoveCartProductDTO removeCartProductDTO);
}

//     PlacementUseCase findUseCaseByName(@RequestHeader(value = "Authorization", required = true) String authorizationHeader, @PathVariable("name") String name);