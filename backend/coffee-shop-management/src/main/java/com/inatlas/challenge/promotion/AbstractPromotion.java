package com.inatlas.challenge.promotion;

import com.inatlas.challenge.products.Product;
import com.inatlas.challenge.utils.CoffeeShopUtils;

import java.util.List;

public abstract class AbstractPromotion {

    protected String name;
    protected boolean perProduct = false;

    //Constructors
    protected AbstractPromotion(final String name) {
        this.name = name;
    }

    protected AbstractPromotion(final String name, final boolean perProduct) {
        this.name = name;
        this.perProduct = perProduct;
    }

    //Getter and Setters
    public String getName() {
        return name;
    }

    public boolean isPerProduct() {
        return perProduct;
    }

    //Abstract methods
    public abstract Double calculateTotal(List<Product> products);
    public abstract void applyPerProduct(List<Product> products);
    public abstract boolean isSuitable(List<Product> products);

    //Common public methods
    public static Double calculateOriginalTotal(final List<Product> products) {
        //Calculates order total price
        final Double total = products.stream()
                .map(p -> p.getName().getPrice() * p.getQuantity())
                .reduce(0.0, (a, b) -> a + b);

        return CoffeeShopUtils.formatDouble(total);
    }

}
