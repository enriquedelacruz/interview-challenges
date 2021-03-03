package com.inatlas.challenge.promotion;

import com.inatlas.challenge.products.Menu;
import com.inatlas.challenge.products.Product;
import com.inatlas.challenge.utils.Utils;

import java.util.List;

public class TotalAmountPromotion extends AbstractPromotion {

    private static final int MINIMUM_AMOUNT = 50;
    private static final double LATTE_NEWPRICE = 3.0;

    public TotalAmountPromotion(String name) {
        super(name, true);
    }

    @Override
    public Double calculateTotal(List<Product> order) {

        //Calculates order total price
        Double total = calculateOriginalTotal(order);

        if (isSuitable(order)) {
            int totalLattes = order.stream()
                    .filter(p -> {
                        return p.getName() == Menu.MenuProduct.LATTE;
                    })
                    .map(Product::getQuantity)
                    .reduce(0, (a, b) -> a + b);

            total -= totalLattes * (Menu.MenuProduct.LATTE.getPrice() - LATTE_NEWPRICE);
        }

        return Utils.formatDouble(total);
    }

    @Override
    public void applyPerProduct(List<Product> products) {
        products.stream()
                .filter(p -> p.getName().equals(Menu.MenuProduct.LATTE))
                .forEach(p -> {
                    p.setPromoPrice(LATTE_NEWPRICE);
                    p.setDiscount(true);
                });
    }

    @Override
    public boolean isSuitable(List<Product> products) {
        return (calculateOriginalTotal(products) >= MINIMUM_AMOUNT);
    }

}
