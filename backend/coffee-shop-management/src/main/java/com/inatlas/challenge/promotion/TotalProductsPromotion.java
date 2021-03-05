package com.inatlas.challenge.promotion;

import com.inatlas.challenge.products.Product;
import com.inatlas.challenge.utils.CoffeeShopUtils;

import java.util.List;

public class TotalProductsPromotion extends AbstractPromotion {

    /**
     * Config property for indicate the minimum quantity of products for the promotion
     */
    private static final int MINIMUM_PRODUCTS = 8;
    /**
     * Config property for indicate the maximum total discount of the promotion
     */
    private static final int DISCOUNT_PERCENTAGE = 5;


    /**
     * Constructor with name parameter
     * @param name name of the espresso promotion
     */
    public TotalProductsPromotion(final String name) {
        super(name, false);
    }

    /**
     * Overridden method to calculate total price with espresso promotion applied
     * @param products product list of the order to be calculated
     * @return total price with promotion applied
     */
    @Override
    public Double calculateTotal(final List<Product> products) {

        //Calculates order total price
        Double total = calculateOriginalTotal(products);

        if (isSuitable(products)) {
            total -= total * DISCOUNT_PERCENTAGE/100;
        }

        return CoffeeShopUtils.formatDouble(total);
    }

    /**
     * Overridden method to apply promotion to every product of the list
     * This promotions is applied per total, so this method doesn't do nothing
     * @param products product list to be applied the promotion
     */
    @Override
    public void applyPerProduct(final List<Product> products) {
        //Nothing to do
    }

    /**
     * Overridden method to determine if the promotion can be applied
     * @param products product list to be analyzed
     * @return true if the promotion can be applied, false otherwise
     */
    @Override
    public boolean isSuitable(final List<Product> products) {
        //Count total products in the order
        final int totalProducts = products.stream().map(Product::getQuantity)
                .reduce(0, (a, b) -> a + b);
        return (totalProducts > MINIMUM_PRODUCTS);
    }

}
