package com.inatlas.challenge;

public enum ProductType {
    SANDWICH ("Sandwich", 10.10),
    LATTE    ("Latte",    5.3),
    ESPRESSO ("Espresso", 4.0);

    String name;
    double price;
    ProductType(String n, double p) {
        name = n;
        price = p;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
