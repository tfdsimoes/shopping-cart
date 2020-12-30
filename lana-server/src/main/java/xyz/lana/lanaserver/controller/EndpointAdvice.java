package xyz.lana.lanaserver.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.lana.lanaserver.exception.CartNotFound;
import xyz.lana.lanaserver.exception.ProductCartNotFound;
import xyz.lana.lanaserver.exception.ProductNotFound;
import xyz.lana.lanaserver.exception.RemoveProductCart;

/**
 * Advice controller to handle exceptions
 */
@RestControllerAdvice
public class EndpointAdvice {
    @ExceptionHandler({CartNotFound.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String cartNotFound(CartNotFound exception) {
        return exception.getMessage();
    }

    @ExceptionHandler({ProductNotFound.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String productNotFound(ProductNotFound exception) {
        return exception.getMessage();
    }

    @ExceptionHandler({ProductCartNotFound.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String productCartNotFound(ProductCartNotFound exception) {
        return exception.getMessage();
    }

    @ExceptionHandler({RemoveProductCart.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String removeProductCart(RemoveProductCart exception) {
        return exception.getMessage();
    }
}
