package xyz.lana.lanaserver.exception;

public class ProductCartNotFound extends RuntimeException {

    public ProductCartNotFound() {
        super("Product does not exist in the cart");
    }
}
