package com.inatlas.challenge.products;

import com.inatlas.challenge.promotion.AbstractPromotion;
import com.inatlas.challenge.promotion.EspressoPromotion;
import com.inatlas.challenge.promotion.TotalAmountPromotion;
import com.inatlas.challenge.promotion.TotalProductsPromotion;

import java.util.Arrays;
import java.util.List;

public class Menu {

    public static enum MenuProduct {
        SANDWICH("Sandwich", 10.10),
        LATTE("Latte", 5.3),
        ESPRESSO("Espresso", 4.0),
        CAPPUCCINO("Cappuccino", 8.0),
        TEA("Tea", 6.1),
        CAKE_SLICE("Cake Slice", 9.0),
        MILK("Milk", 1.0);

        String name;
        double price;

        MenuProduct(String n, double p) {
            name = n;
            price = p;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }
    }

    public static final List<AbstractPromotion> availablePromotions = Arrays.asList(
            new EspressoPromotion("1FreeEspresso x 2Lattes"),
            new TotalProductsPromotion("5%off x 8products"),
            new TotalAmountPromotion("$3Latte x $50order")
    );

}
