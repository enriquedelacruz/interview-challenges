package com.inatlas.challenge;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CoffeeShop {
    public static final String CURRENCY = "$";
    private List<Product> orders = new ArrayList<>();

    public void takeOrder(Menu product, Integer quantity) {
        this.orders.add(new Product(product, quantity));
    }

    public void applyEspressoPromotion() {
        //Detects 2 Lattes for 1 free espresso promotion
        int totalLattes = this.orders.stream()
                .filter(p -> p.getName().equals(Menu.LATTE)) //Filters Latte products
                .reduce(0, (count, p) -> count + p.getQuantity(), Integer::sum); //Summation of quantities of Latte products

        boolean hasMoreThanOneLatte = totalLattes > 1;
        if (hasMoreThanOneLatte) {
            AtomicInteger maxFreeEspressos = new AtomicInteger(totalLattes / 2);
            this.orders.forEach(p -> {
                if (p.getName().equals(Menu.ESPRESSO)) {
                    if (maxFreeEspressos.intValue() > 0) {
                        p.setDiscount(true); //Apply promotion for espresso while we have enough free espressos
                        maxFreeEspressos.getAndDecrement();
                    } else {
                        p.setDiscount(false); //Removes promotion for next espressos with promotion applied before
                    }
                }
            });
        }
    }

    public Double calculateTotal() {
        //Calculates order total price
        Double total = this.orders.stream().map(p -> {
            return p.getPrice();
        }).reduce(0.0, (a, b) -> a + b);

        return total;
    }

    public Double printReceipt() {
        //Calculate promotion
        applyEspressoPromotion();

        //Calculate total amount of receipt
        Double total = calculateTotal();

        //Print receipt by Printer
        Printer.getInstance().printReceipt(this.orders, total);

        return total;
    }

    public void printMenu() {
        // Print whole menu
        Printer.getInstance().printMenu();
    }
}
