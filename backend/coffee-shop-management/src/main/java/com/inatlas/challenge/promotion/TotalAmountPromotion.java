package com.inatlas.challenge.promotion;

import com.inatlas.challenge.products.CoffeeShopMenu;
import com.inatlas.challenge.products.Product;
import com.inatlas.challenge.utils.CoffeeShopUtils;

import java.util.List;

/**
 * Promotion class to represent the 3 latte price for 50 products sold in the order
 * This class is inherited from AbstractPromotion
 */
public class TotalAmountPromotion extends AbstractPromotion {

    /**
     * Config property to indicate the minimum amount of products to apply the promotion
     */
    private static final int MINIMUM_AMOUNT = 50;
    /**
     * Config property to indicate the new price for latte
     */
    private static final double LATTE_NEWPRICE = 3.0;

    /**
     * Constructor with promotion name
     * @param name promotion name
     */
    public TotalAmountPromotion(final String name) {
        super(name, true);
    }

    /**
     * Overridden method to calculate total price with total amount promotion applied
     * @param products product list of the order to be calculated
     * @return total price with promotion applied
     */
    @Override
    public Double calculateTotal(final List<Product> products) {

        //Calculates order total price
        Double total = calculateOriginalTotal(products);

        if (isSuitable(products)) {
            final int totalLattes = products.stream()
                    .filter(p -> p.getMenuProduct() == CoffeeShopMenu.MenuProduct.LATTE)
                    .map(Product::getQuantity)
                    .reduce(0, (a, b) -> a + b);

            total -= totalLattes * (CoffeeShopMenu.MenuProduct.LATTE.getPrice() - LATTE_NEWPRICE);
        }

        return CoffeeShopUtils.formatDouble(total);
    }

    /**
     * Overridden method to apply promotion to every product of the list
     * @param products product list to be applied the promotion
     */
    @Override
    public void applyPerProduct(final List<Product> products) {
        products.stream()
                .filter(p -> p.getMenuProduct().equals(CoffeeShopMenu.MenuProduct.LATTE))
                .forEach(p -> {
                    p.setPromoPrice(LATTE_NEWPRICE);
                    p.setDiscount(true);
                });
    }

    /**
     * Overriden method to determine if the promotion can be applied
     * @param products product list to be analyzed
     * @return true if the promotion can be applied, false otherwise
     */
    @Override
    public boolean isSuitable(final List<Product> products) {
        return (calculateOriginalTotal(products) >= MINIMUM_AMOUNT);
    }

}
