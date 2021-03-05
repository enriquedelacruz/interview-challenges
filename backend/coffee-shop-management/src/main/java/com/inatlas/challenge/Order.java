package com.inatlas.challenge;

import com.inatlas.challenge.products.CoffeeShopMenu;
import com.inatlas.challenge.products.Product;
import com.inatlas.challenge.promotion.AbstractPromotion;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Order class to represent the products ordered
 */
public class Order {

    /**
     * Product list of the order
     */
    private List<Product> products;
    /**
     * Total price of the order
     */
    private Double total;
    /**
     * Name of the applied promotion, null otherwise
     */
    private String appliedPromotion;
    /**
     * Current date of the order
     */
    private Date date;

    //Constructors

    /**
     * Default constructor. Initializes the class members
     */
    public Order() {
        this.products = new ArrayList<>();
        this.total = 0.0;
        this.date = new Date();
    }

    //Getters and Setters

    /**
     * Getter method to get the product list of the order
     * @return the product list of the order
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     * Setter method to set the product list of the order
     * @param products the product list to be set to the order
     */
    public void setProducts(final List<Product> products) {
        this.products = products;
    }

    /**
     * Getter method to get the total price of the order
     * @return the total price of the order
     */
    public Double getTotal() {
        return total;
    }

    /**
     * Getter method to get the current date of the order
     * @return the current date of the order
     */
    public Date getDate() {
        return date;
    }

    /**
     * Setter method to set the current date of the order
     * @param date the current date of the order
     */
    public void setDate(final Date date) {
        this.date = date;
    }

    /**
     * Getter method to get the applied promotion to the order
     * @return the name promotion applied to the order
     */
    public String getAppliedPromotion() {
        return appliedPromotion;
    }

    //Public methods

    /**
     * Method to take the order, it is used to add a product to the order
     * @param product Type of the product to add to the order
     * @param quantity Quantity of the product sold
     */
    public void takeOrder(final CoffeeShopMenu.MenuProduct product, final Integer quantity) {
        this.products.add(new Product(product, quantity));
    }

    /**
     * Calculate total price of the order without review if there is any promotion can be applied
     * @return the total price of the order without promotions
     */
    public Double calculateTotalWithoutPromotions() {
        this.total = AbstractPromotion.calculateOriginalTotal(this.products);
        return this.total;
    }

    /**
     * Calculate total price of the order reviewing a possible promotion to be applied
     * @return the total price of the order with promotions applied
     */
    public Double calculateTotal() {
        //Decides what promotion will be applied (the cheapest) or none
        final AbstractPromotion cheapestPromotion = CoffeeShopMenu.getAvailablePromotions().stream()
                .filter(p -> p.isSuitable(this.products))
                .min(Comparator.comparingDouble(x -> x.calculateTotal(this.products))).orElse(null);

        //If there isn't any promotion, total price will be the original price without promotions
        if (cheapestPromotion == null) {
            this.total = AbstractPromotion.calculateOriginalTotal(this.products);
        } else {
            this.appliedPromotion = cheapestPromotion.getName();
            this.total = cheapestPromotion.calculateTotal(this.products);
            //If cheapest promotion is per product, applies discount in every product
            if (cheapestPromotion.isPerProduct()) {
                cheapestPromotion.applyPerProduct(this.products);
            }
        }

        return this.total;
    }

    /**
     * Method to print the receipt for the order
     * @return the total price of the order
     */
    public Double printReceipt() {
        //Calculate total amount of receipt
        this.total = calculateTotal();

        //Print receipt by Printer
        Printer.getInstance().printReceipt(this.products, this.total, this.appliedPromotion);

        return this.total;
    }

}
