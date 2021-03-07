package com.inatlas.challenge;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Client class to represent the clients of the coffe shop
 */
public class Client {

    /**
     * Client Id
     */
    private int clientId;

    /**
     * Order list of the client
     */
    private List<Order> orders;

    //Constructors

    /**
     * Default constructor to create the Client
     */
    public Client() {
        this.orders = new ArrayList<>();
        this.orders.add(new Order()); //Initialize with the first order
        this.clientId = this.hashCode();
    }

    //Getters and Setters

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    /**
     * Getter method to get the order list
     * @return the order list of client
     */
    public List<Order> getOrders() {
        return orders;
    }

    /**
     * Method to find an order by id
     * @param orderId order id
     * @return the order
     */
    public Order getOrder(int orderId) {
        Order order = null;
        if (orders != null && !orders.isEmpty()) {
            List<Order> orderList = orders.stream()
                    .filter(c -> c.getOrderId() == orderId)
                    .collect(Collectors.toList());
            if (orderList != null && !orderList.isEmpty()) {
                order = orderList.get(0);
            }
        }

        return order;
    }


    //Public methods

    /**
     * Method to add a new order and add to the list
     * @return returns the new order created
     */
    public Order registerNewOrder() {
        if (this.orders == null) {
            this.orders = new ArrayList<>();
        }
        final Order newOrder = new Order();
        this.orders.add(newOrder);

        return newOrder;
    }

    /**
     * Method to find the orders into a date range
     * @param beginDate inclusive begin date of the range
     * @param endDate inclusive end date of the range
     * @return the list of orders found into the range
     */
    public List<Order> findOrdersByDateRange(final Date beginDate, final Date endDate) {

        List<Order> ordersFound = new ArrayList<>();
        if (beginDate != null && endDate != null ) {
            ordersFound = this.orders.stream()
                    .filter(o -> (o.getDate().compareTo(beginDate) >= 0) && (o.getDate().compareTo(endDate) <= 0))
                    .collect(Collectors.toList());
        }

        return ordersFound;

    }

    /**
     * Method to count the orders found into the date range
     * @param beginDate inclusive begin date of the range
     * @param endDate inclusive end date of the range
     * @return the number of orders found into the date range
     */
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
