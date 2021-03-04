package com.inatlas.challenge;

import com.inatlas.challenge.products.Menu;
import com.inatlas.challenge.products.Product;
import com.inatlas.challenge.promotion.AbstractPromotion;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Order {

    public static final String CURRENCY = "$";
    private List<Product> products;
    private Double total;
    private String appliedPromotion;
    private Date date;

    //Constructors
    public Order() {
        this.products = new ArrayList<>();
        this.total = 0.0;
        this.date = new Date();
    }

    //Getters and Setters
    public Double getTotal() {
        return total;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    //Public methods
    public void takeOrder(Menu.MenuProduct product, Integer quantity) {
        this.products.add(new Product(product, quantity));
    }

    public Double calculateTotal() {
        AbstractPromotion cheapestPromotion = Menu.availablePromotions.stream()
                .filter(p -> p.isSuitable(this.products))
                .min(Comparator.comparingDouble(x -> x.calculateTotal(this.products))).orElse(null);

        if (cheapestPromotion != null) {
            this.appliedPromotion = cheapestPromotion.getName();
            this.total = cheapestPromotion.calculateTotal(this.products);
            //If cheapest promotion is per product, applies discount in every product
            if (cheapestPromotion.isPerProduct()) {
                cheapestPromotion.applyPerProduct(this.products);
            }
        } else {
            this.total = AbstractPromotion.calculateOriginalTotal(this.products);
        }

        return this.total;
    }

    public Double printReceipt() {
        //Calculate total amount of receipt
        this.total = calculateTotal();

        //Print receipt by Printer
        Printer.getInstance().printReceipt(this.products, this.total, this.appliedPromotion);

        return this.total;
    }

}
