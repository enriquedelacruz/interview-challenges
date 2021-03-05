package com.inatlas.challenge.products;


import com.inatlas.challenge.utils.CoffeeShopUtils;

public class Product {

    private final CoffeeShopMenu.MenuProduct name;
    private Integer quantity;
    private boolean discount;
    private Double promoPrice;

    public Product(final CoffeeShopMenu.MenuProduct name, final Integer qtt) {
        this.name = name;
        this.quantity = qtt;
        this.promoPrice = name.getPrice();
    }

    public Product(final CoffeeShopMenu.MenuProduct name, final Integer qtt, final boolean discount) {
        this.name = name;
        this.quantity = qtt;
        this.discount = discount;
        this.promoPrice = name.getPrice();
    }

    public Product(final CoffeeShopMenu.MenuProduct name, final Integer qtt, final boolean discount, final Double promoPrice) {
        this.name = name;
        this.quantity = qtt;
        this.discount = discount;
        this.promoPrice = promoPrice;
    }

    @Override
    public String toString() {
        return name.getName() + " " + getPrice() + (discount?" - promotion":"");
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(final Integer quantity) {
        this.quantity = quantity;
    }

    public CoffeeShopMenu.MenuProduct getName() {
        return name;
    }

    public Double getPrice() {
        Double price;

        if (discount) {
            price = CoffeeShopUtils.formatDouble(this.promoPrice) * this.quantity;
        } else {
            price = CoffeeShopUtils.formatDouble(this.name.getPrice()) * this.quantity;
        }

        return price;
    }

    public boolean isDiscount() {
        return discount;
    }

    public void setDiscount(final boolean discount) {
        this.discount = discount;
    }

    public Double getPromoPrice() {
        return promoPrice;
    }

    public void setPromoPrice(final Double promoPrice) {
        this.promoPrice = promoPrice;
    }
}
