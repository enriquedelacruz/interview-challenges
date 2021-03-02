package com.inatlas.challenge;


import com.inatlas.challenge.utils.Utils;

public class Product {

    private Menu name;
    private Integer quantity;
    private boolean discount;

    public Product(Menu name, Integer qtt) {
        this.name = name;
        this.quantity = qtt;
    }

    public Product(Menu name, Integer qtt, boolean discount) {
        this.name = name;
        this.quantity = qtt;
        this.discount = discount;
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

    public Menu getName() {
        return name;
    }

    public Double getPrice() {
        Double price = 0.0;

        if (!discount) {
            price = Utils.formatDouble(this.name.getPrice()) * this.quantity;
        }

        return price;
    }

    public boolean isDiscount() {
        return discount;
    }

    public void setDiscount(boolean discount) {
        this.discount = discount;
    }
}
