package com.inatlas.challenge.promotion;

import com.inatlas.challenge.products.CoffeeShopMenu;
import com.inatlas.challenge.products.Product;
import com.inatlas.challenge.utils.CoffeeShopUtils;

import java.util.List;

public class TotalAmountPromotion extends AbstractPromotion {

    private static final int MINIMUM_AMOUNT = 50;
    private static final double LATTE_NEWPRICE = 3.0;

    public TotalAmountPromotion(final String name) {
        super(name, true);
    }

    @Override
    public Double calculateTotal(final List<Product> order) {

        //Calculates order total price
        Double total = calculateOriginalTotal(order);

        if (isSuitable(order)) {
            final int totalLattes = order.stream()
                    .filter(p -> p.getName() == CoffeeShopMenu.MenuProduct.LATTE)
                    .map(Product::getQuantity)
                    .reduce(0, (a, b) -> a + b);

            total -= totalLattes * (CoffeeShopMenu.MenuProduct.LATTE.getPrice() - LATTE_NEWPRICE);
        }

        return CoffeeShopUtils.formatDouble(total);
    }

    @Override
    public void applyPerProduct(final List<Product> products) {
        products.stream()
                .filter(p -> p.getName().equals(CoffeeShopMenu.MenuProduct.LATTE))
                .forEach(p -> {
                    p.setPromoPrice(LATTE_NEWPRICE);
                    p.setDiscount(true);
                });
    }

    @Override
    public boolean isSuitable(final List<Product> products) {
        return (calculateOriginalTotal(products) >= MINIMUM_AMOUNT);
    }

}
