package com.inatlas.challenge;

import java.util.ArrayList;
import java.util.List;

public class CoffeeShop {
    private List<Product> orders = new ArrayList<>();

    public void takeOrder(String product, Integer qtt) {
        this.orders.add(new Product(product, qtt));
    }

    public Double printReceipt() {
        System.out.println("======================================");
        boolean hasMoreThanOneLatte = this.orders.stream().anyMatch(p -> p.getName().equals("Latte") && p.getQtt() > 1);
        if (hasMoreThanOneLatte) {
            this.orders.forEach(p -> {
                if (p.getName().equals("Espresso")) {
                    p.setDiscount(true);
                }
            });
        }
        Double total = this.orders.stream().map(p -> {
            System.out.println(p);
            return Double.valueOf(p.getPrice().split("\\$")[1]);
        }).reduce(0.0, (a, b) -> a + b);
        System.out.println("----------------");
        System.out.println("Total: $" + total);
        System.out.println("======================================");

        return total;
    }

    public void printMenu() {
        // Print whole menu
    }
}
