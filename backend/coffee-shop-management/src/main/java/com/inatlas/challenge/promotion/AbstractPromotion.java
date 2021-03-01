package com.inatlas.challenge.promotion;

import com.inatlas.challenge.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public abstract class AbstractPromotion {

    protected String name;

    public AbstractPromotion(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract Double calculateTotal(List<Product> products);

    public Double calculateOriginalTotal(List<Product> products) {
        //Calculates order total price
        Double total = products.stream()
                .map(p -> {
                    return p.getName().getPrice() * p.getQuantity();
                }).reduce(0.0, (a, b) -> a + b);

        return formatDouble(total);
    }

    protected Double formatDouble(Double d) {
        BigDecimal bd = new BigDecimal(d).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
