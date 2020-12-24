package xyz.lana.lanaserver.entity;

import java.util.UUID;

public class CartProduct {

    private String id;

    private String productId;

    private int quantity;

    public CartProduct(String productId, int quantity) {
        id = UUID.randomUUID().toString();
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
