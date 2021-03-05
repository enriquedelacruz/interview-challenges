package com.inatlas.challenge.promotion;

import com.inatlas.challenge.products.Product;
import com.inatlas.challenge.utils.CoffeeShopUtils;

import java.util.List;

/**
 * Abstract class to implement a generic promotion to be applied to an order.
 * A Strategy pattern is implemented to create several promotions inherited of this
 * in order to implement different behavior in different promotions (mostly for calculate total price)
 */
public abstract class AbstractPromotion {

    /**
     * Name of the promotion
     */
    protected String name;
    /**
     * Boolean property to determine the promotion if is applied per product or per total order
     */
    protected boolean perProduct = false;

    //Constructors

    /**
     * Constructor of class
     * @param name name of the promotion
     */
    protected AbstractPromotion(final String name) {
        this.name = name;
    }

    /**
     * Constructor of class
     * @param name name of the promotion
     * @param perProduct boolean value to determine if the promotion is per product or per total
     */
    protected AbstractPromotion(final String name, final boolean perProduct) {
        this.name = name;
        this.perProduct = perProduct;
    }

    //Getter and Setters

    /**
     * Getter method to get the name of the promotion
     * @return promotion name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter method to get if a promotion is per product or per total
     * @return boolean value to determine if the promotion is per product or per total
     */
    public boolean isPerProduct() {
        return perProduct;
    }

    //Abstract methods

    /**
     * Abstract method to calculate the total price of the order
     * @param products product list of the order to be calculated
     * @return double number for the total price of the order
     */
    public abstract Double calculateTotal(List<Product> products);

    /**
     * Abstract method to apply the promotion to every product (if the promotion is per product)
     * @param products product list to be applied the promotion
     */
    public abstract void applyPerProduct(List<Product> products);

    /**
     * Abstract method to decide if a promotion can be applied to the order
     * @param products product list to be analyzed
     * @return true if the promotion can be applied, false otherwise
     */
    public abstract boolean isSuitable(List<Product> products);

    //Common public methods

    /**
     * Static method to calculate the total price without promotion apply
     * @param products product list to be calculated
     * @return double number of the total price
     */
    public static Double calculateOriginalTotal(final List<Product> products) {
        //Calculates order total price
        final Double total = products.stream()
                .map(p -> p.getMenuProduct().getPrice() * p.getQuantity())
                .reduce(0.0, (a, b) -> a + b);

        return CoffeeShopUtils.formatDouble(total);
    }

}
