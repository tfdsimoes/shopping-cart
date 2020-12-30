package xyz.lana.lanaserver.exception;

public class RemoveProductCart extends RuntimeException{
    public RemoveProductCart() {
        super("Quantity to delete is bigger then existent");
    }
}
