package xyz.lana.lanaserver.exception;

public class CartNotFound extends RuntimeException {

    public CartNotFound() {
        super("Cart does not exist, initialize first");
    }
}
