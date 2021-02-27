package com.inatlas.challenge;


public class Product {

    private ProductType name;
    private Integer quantity;
    private boolean discount;

    public Product(ProductType name, Integer qtt) {
        this.name = name;
        this.quantity = qtt;
    }

    @Override
    public String toString() {
        return name.getName() + " " + getPrice() + (discount?" - promotion":"");
    }

    public Integer getQuantity() {
        return quantity;
    }

    public ProductType getName() {
        return name;
    }

    public Double getPrice() {
        Double price = 0.0;

        if (!discount) {
            price = this.name.getPrice() * this.quantity;
        }

        return price;
    }

    public void setDiscount(boolean discount) {
        this.discount = discount;
    }
}
