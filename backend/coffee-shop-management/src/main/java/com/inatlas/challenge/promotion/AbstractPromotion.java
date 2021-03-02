package com.inatlas.challenge.promotion;

import com.inatlas.challenge.Product;
import com.inatlas.challenge.utils.Utils;

import java.util.List;

public abstract class AbstractPromotion {

    protected String name;
    protected boolean isPerProduct = false;

    //Constructors
    public AbstractPromotion(String name) {
        this.name = name;
    }

    public AbstractPromotion(String name, boolean isPerProduct) {
        this.name = name;
        this.isPerProduct = isPerProduct;
    }

    //Getter and Setters
    public String getName() {
        return name;
    }

    public boolean isPerProduct() {
        return isPerProduct;
    }

    //Abstract methods
    public abstract Double calculateTotal(List<Product> products);
    public abstract void applyPerProduct(List<Product> products);
    public abstract boolean isSuitable(List<Product> products);

    //Common public methods
    public static Double calculateOriginalTotal(List<Product> products) {
        //Calculates order total price
        Double total = products.stream()
                .map(p -> {
                    return p.getName().getPrice() * p.getQuantity();
                }).reduce(0.0, (a, b) -> a + b);

        return Utils.formatDouble(total);
    }

}
