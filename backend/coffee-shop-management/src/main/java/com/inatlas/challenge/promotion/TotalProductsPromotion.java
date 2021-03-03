package com.inatlas.challenge.promotion;

import com.inatlas.challenge.products.Product;
import com.inatlas.challenge.utils.Utils;

import java.util.List;

public class TotalProductsPromotion extends AbstractPromotion {

    private static final int MINIMUM_PRODUCTS = 8;
    private static final int DISCOUNT_PERCENTAGE = 5;

    public TotalProductsPromotion(String name) {
        super(name, false);
    }

    @Override
    public Double calculateTotal(List<Product> order) {

        //Calculates order total price
        Double total = calculateOriginalTotal(order);

        if (isSuitable(order)) {
            total -= total * DISCOUNT_PERCENTAGE/100;
        }

        return Utils.formatDouble(total);
    }

    @Override
    public void applyPerProduct(List<Product> products) {
        //Nothing to do
    }

    @Override
    public boolean isSuitable(List<Product> products) {
        //Count total products in the order
        int totalProducts = products.stream().map(Product::getQuantity)
                .reduce(0, (a, b) -> a + b);
        return (totalProducts > MINIMUM_PRODUCTS);
    }

}
