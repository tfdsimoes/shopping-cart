package xyz.lana.lanaserver.exception;

public class ProductNotFound extends RuntimeException {

    public ProductNotFound() {
        super("Product to add in the cart does not exist");
    }
}
