package com.inatlas.challenge.products;

import com.inatlas.challenge.Printer;
import com.inatlas.challenge.promotion.AbstractPromotion;
import com.inatlas.challenge.promotion.EspressoPromotion;
import com.inatlas.challenge.promotion.TotalAmountPromotion;
import com.inatlas.challenge.promotion.TotalProductsPromotion;

import java.util.Arrays;
import java.util.List;

/**
 * Class that represents the menu of the coffee shop
 * To include new products, the only thing to do is add the new product into de enum property
 */
public class CoffeeShopMenu {

    /**
     * Enumerator of the products offered by the coffe shop
     */
    public enum MenuProduct {
        SANDWICH("Sandwich", 10.10),
        LATTE("Latte", 5.3),
        ESPRESSO("Espresso", 4.0),
        CAPPUCCINO("Cappuccino", 8.0),
        TEA("Tea", 6.1),
        CAKE_SLICE("Cake Slice", 9.0),
        MILK("Milk", 1.0);

        //name property of a product
        String name;
        //price property of a product
        double price;

        /**
         * Constructor of the enum item
         * @param name name of the product in the menu
         * @param price price of the product in the menu
         */
        MenuProduct(final String name, final double price) {
            this.name = name;
            this.price = price;
        }

        /**
         * Getter method to get the name of the product item
         * @return the name of product item
         */
        public String getName() {
            return name;
        }

        /**
         * Getter method to get the price of the product item
         * @return the price of the product item
         */
        public double getPrice() {
            return price;
        }
    }

    /**
     * Property for the available promotions that can be applied to the orders.
     * If you want to create a new promotion you must to create an inherited class of AbstractPromotion
     * and add to this available promotion list.
     */
    protected static final List<AbstractPromotion> availablePromotions = Arrays.asList(
            new EspressoPromotion("1FreeEspresso x 2Lattes"),
            new TotalProductsPromotion("5%off x 8products"),
            new TotalAmountPromotion("$3Latte x $50order")
    );

    /**
     * Getter method to get the available promotion list
     * @return the available promotion list
     */
    public static List<AbstractPromotion> getAvailablePromotions() {
        return availablePromotions;
    }

    /**
     * Method to print the whole menu
     */
    public static void printMenu() {
        // Print whole menu
        Printer.getInstance().printMenu();
    }

}
