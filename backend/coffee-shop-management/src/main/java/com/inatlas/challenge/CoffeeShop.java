package com.inatlas.challenge;

import com.inatlas.challenge.promotion.AbstractPromotion;
import com.inatlas.challenge.promotion.EspressoPromotion;
import com.inatlas.challenge.promotion.TotalProductsPromotion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class CoffeeShop {

    public static final String CURRENCY = "$";
    private List<Product> orders = new ArrayList<>();
    private List<AbstractPromotion> availablePromotions = Arrays.asList(
            new EspressoPromotion("1FreeEspresso x 2Lattes"),
            new TotalProductsPromotion("5%off x 8products")
    );
    private Double total = 0.0;
    private String appliedPromotion;

    public void takeOrder(Menu product, Integer quantity) {
        this.orders.add(new Product(product, quantity));
    }

    public Double calculateTotal() {
        AbstractPromotion cheapestPromotion = availablePromotions.stream()
                .min(Comparator.comparingDouble(x -> x.calculateTotal(this.orders))).get();

        this.appliedPromotion = cheapestPromotion.getName();
        this.total = cheapestPromotion.calculateTotal(this.orders);

        return total;
    }

    public Double printReceipt() {
        //Calculate total amount of receipt
        Double total = calculateTotal();

        //Print receipt by Printer
        Printer.getInstance().printReceipt(this.orders, this.total, this.appliedPromotion);

        return total;
    }

    public void printMenu() {
        // Print whole menu
        Printer.getInstance().printMenu();
    }

}
