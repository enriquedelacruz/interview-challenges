package com.inatlas.challenge.promotion;

import com.inatlas.challenge.Product;

import java.util.List;

public class TotalProductsPromotion extends AbstractPromotion {

    private static final int MINIMUM_PRODUCTS = 8;
    private static final int DISCOUNT_PERCENTAGE = 5;

    public TotalProductsPromotion(String name) {
        super(name);
    }

    @Override
    public Double calculateTotal(List<Product> order) {

        //Calculates order total price
        Double total = calculateOriginalTotal(order);

        int totalProducts = order.stream().map(Product::getQuantity)
                .reduce(0, (a, b) -> a + b);
        if (totalProducts > MINIMUM_PRODUCTS) {
            total -= total * DISCOUNT_PERCENTAGE/100;
        }

        return formatDouble(total);
    }

}
