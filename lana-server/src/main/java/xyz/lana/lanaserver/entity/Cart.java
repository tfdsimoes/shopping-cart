package xyz.lana.lanaserver.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Cart {

    private String id;

    private List<CartProduct> products = new ArrayList<>();

    private BigDecimal amount = BigDecimal.ZERO;

    public Cart() {
        id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public List<CartProduct> getProducts() {
        return products;
    }

    public void setProducts(List<CartProduct> products) {
        this.products = products;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
