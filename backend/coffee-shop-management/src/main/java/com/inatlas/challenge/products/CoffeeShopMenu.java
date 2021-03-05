package com.inatlas.challenge.products;

import com.inatlas.challenge.Printer;
import com.inatlas.challenge.promotion.AbstractPromotion;
import com.inatlas.challenge.promotion.EspressoPromotion;
import com.inatlas.challenge.promotion.TotalAmountPromotion;
import com.inatlas.challenge.promotion.TotalProductsPromotion;

import java.util.Arrays;
import java.util.List;

public class CoffeeShopMenu {

    public enum MenuProduct {
        SANDWICH("Sandwich", 10.10),
        LATTE("Latte", 5.3),
        ESPRESSO("Espresso", 4.0),
        CAPPUCCINO("Cappuccino", 8.0),
        TEA("Tea", 6.1),
        CAKE_SLICE("Cake Slice", 9.0),
        MILK("Milk", 1.0);

        String name;
        double price;

        MenuProduct(final String name, final double price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }
    }

    protected static final List<AbstractPromotion> availablePromotions = Arrays.asList(
            new EspressoPromotion("1FreeEspresso x 2Lattes"),
            new TotalProductsPromotion("5%off x 8products"),
            new TotalAmountPromotion("$3Latte x $50order")
    );

    public static List<AbstractPromotion> getAvailablePromotions() {
        return availablePromotions;
    }

    public static void printMenu() {
        // Print whole menu
        Printer.getInstance().printMenu();
    }

}
