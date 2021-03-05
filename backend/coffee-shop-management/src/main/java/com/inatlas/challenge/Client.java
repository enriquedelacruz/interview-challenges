package com.inatlas.challenge;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Client {

    private List<Order> orders;

    //Constructors
    public Client() {
        this.orders = new ArrayList<>();
        this.orders.add(new Order()); //Initialize with the first order
    }

    //Getters and Setters
    public List<Order> getOrders() {
        return orders;
    }

    //Public methods
    public Order registerNewOrder() {
        if (this.orders == null) {
            this.orders = new ArrayList<>();
        }
        final Order newOrder = new Order();
        this.orders.add(newOrder);

        return newOrder;
    }

    public void printAllReceipts() {
        orders.stream().forEach(Order::printReceipt);
    }

    public List<Order> findOrdersByDateRange(final Date beginDate, final Date endDate) {

        List<Order> ordersFound = new ArrayList<>();
        if (beginDate != null && endDate != null ) {
            ordersFound = this.orders.stream()
                    .filter(o -> (o.getDate().compareTo(beginDate) >= 0) && (o.getDate().compareTo(endDate) <= 0))
                    .collect(Collectors.toList());
        }

        return ordersFound;

    }

    public int countOrdersByDateRange(final Date beginDate, final Date endDate) {

        int count = 0;
        if (beginDate != null && endDate != null) {
            final List<Order> ordersFound = findOrdersByDateRange(beginDate, endDate);
            if (ordersFound != null) {
                count = ordersFound.size();
            }
        }

        return count;

    }

}
