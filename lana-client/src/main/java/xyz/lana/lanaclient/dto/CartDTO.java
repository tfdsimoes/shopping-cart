package xyz.lana.lanaclient.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CartDTO {

    private String id;

    private List<CartProductDTO> products = new ArrayList<>();

    private BigDecimal amount = BigDecimal.ZERO;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<CartProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<CartProductDTO> products) {
        this.products = products;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
