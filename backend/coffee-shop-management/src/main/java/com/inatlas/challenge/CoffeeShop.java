package com.inatlas.challenge;

import com.inatlas.challenge.promotion.AbstractPromotion;
import com.inatlas.challenge.promotion.EspressoPromotion;
import com.inatlas.challenge.promotion.TotalAmountPromotion;
import com.inatlas.challenge.promotion.TotalProductsPromotion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class CoffeeShop {

    public static final String CURRENCY = "$";
    private List<Product> orders = new ArrayList<>();
    private final List<AbstractPromotion> availablePromotions = Arrays.asList(
            new EspressoPromotion("1FreeEspresso x 2Lattes"),
            new TotalProductsPromotion("5%off x 8products"),
            new TotalAmountPromotion("$3Latte x $50order")
    );
    private Double total = 0.0;
    private String appliedPromotion;

    public void takeOrder(Menu product, Integer quantity) {
        this.orders.add(new Product(product, quantity));
    }

    public Double calculateTotal() {
        AbstractPromotion cheapestPromotion = availablePromotions.stream()
                .filter(p -> p.isSuitable(this.orders))
                .min(Comparator.comparingDouble(x -> x.calculateTotal(this.orders))).orElse(null);

        if (cheapestPromotion != null) {
            this.appliedPromotion = cheapestPromotion.getName();
            this.total = cheapestPromotion.calculateTotal(this.orders);
            //If cheapest promotion is per product, applies discount in every product
            if (cheapestPromotion.isPerProduct()) {
                cheapestPromotion.applyPerProduct(this.orders);
            }
        } else {
            this.total = AbstractPromotion.calculateOriginalTotal(this.orders);
        }

        return this.total;
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
