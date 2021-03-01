package com.inatlas.challenge.promotion;

import com.inatlas.challenge.Menu;
import com.inatlas.challenge.Product;

import java.util.List;

public class EspressoPromotion extends AbstractPromotion {

    public EspressoPromotion(String name) {
        super(name);
    }

    @Override
    public Double calculateTotal(List<Product> products) {

        int totalLattes = products.stream()
                .filter(p -> {
                    return p.getName() == Menu.LATTE;
                })
                .map(Product::getQuantity)
                .reduce(0, (a, b) -> a + b);
        int totalEspressos = products.stream()
                .filter(p -> {
                    return p.getName() == Menu.ESPRESSO;
                })
                .map(Product::getQuantity)
                .reduce(0, (a, b) -> a + b);
        int maxFreeEspressos = totalLattes / 2;

        Double total = calculateOriginalTotal(products);
        if (maxFreeEspressos > 0) {
            total -= maxFreeEspressos * Menu.ESPRESSO.getPrice();
        }

        return formatDouble(total);
    }

}
