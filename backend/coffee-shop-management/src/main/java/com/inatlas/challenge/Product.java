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
        return name + " " + getPrice();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public ProductType getName() {
        return name;
    }

    public String getPrice() {
        Double price = 0.0;

        if (!discount && Menu.products.get(this.name) != null) {
            price = Menu.products.get(this.name) * this.quantity;
        }

        return "$ " +  price;
    }

    public void setDiscount(boolean discount) {
        this.discount = discount;
    }
}
