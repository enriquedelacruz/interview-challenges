package com.inatlas.challenge.products;


import com.inatlas.challenge.utils.Utils;

public class Product {

    private Menu.MenuProduct name;
    private Integer quantity;
    private boolean discount;
    private Double promoPrice;

    public Product(Menu.MenuProduct name, Integer qtt) {
        this.name = name;
        this.quantity = qtt;
        this.promoPrice = name.getPrice();
    }

    public Product(Menu.MenuProduct name, Integer qtt, boolean discount) {
        this.name = name;
        this.quantity = qtt;
        this.discount = discount;
        this.promoPrice = name.getPrice();
    }

    public Product(Menu.MenuProduct name, Integer qtt, boolean discount, Double promoPrice) {
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

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Menu.MenuProduct getName() {
        return name;
    }

    public Double getPrice() {
        Double price = 0.0;

        if (!discount) {
            price = Utils.formatDouble(this.name.getPrice()) * this.quantity;
        } else {
            price = Utils.formatDouble(this.promoPrice) * this.quantity;
        }

        return price;
    }

    public boolean isDiscount() {
        return discount;
    }

    public void setDiscount(boolean discount) {
        this.discount = discount;
    }

    public Double getPromoPrice() {
        return promoPrice;
    }

    public void setPromoPrice(Double promoPrice) {
        this.promoPrice = promoPrice;
    }
}
