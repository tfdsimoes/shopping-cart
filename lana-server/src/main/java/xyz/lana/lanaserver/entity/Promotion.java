package xyz.lana.lanaserver.entity;

import xyz.lana.lanaserver.entity.enums.PromotionType;

public class Promotion {

    private String id;

    private String code;

    private String productId;

    private int discount;

    private PromotionType type;

    private int minQuantity;

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String idProduct) {
        this.productId = idProduct;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public PromotionType getType() {
        return type;
    }

    public void setType(PromotionType type) {
        this.type = type;
    }

    public int getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(int minQuantity) {
        this.minQuantity = minQuantity;
    }
}
