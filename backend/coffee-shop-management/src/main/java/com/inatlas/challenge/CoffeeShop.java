package com.inatlas.challenge;

import com.inatlas.challenge.products.CoffeeShopMenu;
import com.inatlas.challenge.products.Product;
import com.inatlas.challenge.utils.CoffeeShopUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * Singleton class for CoffeeShop. It represents the coffeeShop object.
 * Every app in each isolated JVM can be a different shop.
 */
public class CoffeeShop {

    /**
     * CURRENCY config constant of coffee shop
     */
    public static final String CURRENCY = "$";

    /**
     * Instance of CoffeeShop to implements the singleton
     */
    private static CoffeeShop coffeeShopInstance;
    /**
     * List of coffee shop clients
     */
    private List<Client> clients;

    //Constructors

    /**
     * Default constructors. Initializes the client list
     */
    public CoffeeShop() {
        this.clients = new ArrayList<>();
    }

    /**
     * Constructor to assign a client list
     * @param clients client list to be initializated
     */
    public CoffeeShop(final List<Client> clients) {
        this.clients = clients;
    }

    //Getters and Setters

    /**
     * Method to get the singleton instance of CoffeeShop
     * @return the CoffeeShop instance
     */
    public static synchronized CoffeeShop getInstance() {
        if (coffeeShopInstance == null) {
            coffeeShopInstance = new CoffeeShop();
        }
        return coffeeShopInstance;
    }

    /**
     * Getter method to get the client list
     * @return shop client list
     */
    public List<Client> getClients() {
        return clients;
    }

    /**
     * Setter method to set the client list
     * @param clients shop client list
     */
    public void setClients(final List<Client> clients) {
        this.clients = clients;
    }

    //Public methods

    /**
     * Method to print the whole menu
     */
    public void printMenu() {
        CoffeeShopMenu.printMenu();
    }

    /**
     * Method to add a new client to the coffee shop
     */
    public void registerNewClient() {
        clients.add(new Client());
    }

    /**
     * Method to count the number of clients whith orders in a date range
     * @param beginDate inclusive begin date of range
     * @param endDate inclusive end date of range
     * @return the number of clients whith orders in a date range
     */
    public int countClientsByDateRange(final Date beginDate, final Date endDate) {

        int count = 0;
        if (beginDate != null && endDate != null) {
            count = this.clients.stream()
                    .map(c -> c.countOrdersByDateRange(beginDate, endDate))
                    .reduce(count, (a, b) -> a + b);
        }

        return count;

    }

    /**
     * Method to generate a 'virtual' Order, as report, to store the daily products sold in a specific day
     * @param day the specific day to generate the daily report
     * @return a 'virtual' Order, as daily report of products sold
     */
    public Order listDailyProductsSold(final Date day) {

        //First gets the order list of the day
        List<Order> dailyOrders = null;
        if (day != null) {
            dailyOrders = this.clients.stream()
                    .map(c -> c.findOrdersByDateRange(day, day))
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
        }

        //Second, create an order to put all the products in the menu
        final Order dailyOrderSummary = new Order();
        dailyOrderSummary.setDate(day);
        final List<Product> dailyProducts = dailyOrderSummary.getProducts();
        if (dailyOrders == null || dailyOrders.isEmpty()) {
            //For each menu product adds a product
            Arrays.stream(CoffeeShopMenu.MenuProduct.values())
                    .forEach(mp -> dailyProducts.add(new Product(mp, 0)));

        } else {
            final List<Product> allDailyProducts = dailyOrders.stream()
                    .map(Order::getProducts)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());

            //For each menu product, sums the quantity of these products that are been sold
            Arrays.stream(CoffeeShopMenu.MenuProduct.values())
                    .forEach(
                            mp -> {
                                final int productQuantity = allDailyProducts.stream()
                                        .filter(p -> p.getMenuProduct() == mp)
                                        .map(Product::getQuantity)
                                        .reduce((a,b) -> a + b).orElse(0);

                                dailyProducts.add(new Product(mp, productQuantity));
                            }
                    );
        }
        dailyOrderSummary.calculateTotalWithoutPromotions();

        return dailyOrderSummary;

    }

    /**
     * Method to print the daily products sold in a specific day
     * @param day the specific day to generate the daily report
     */
    public void printDailyProductsSold(final Date day) {

        if (day != null) {
            final Order dailyOrderSummary = listDailyProductsSold(day);
            if (dailyOrderSummary != null) {
                Printer.getInstance().printDailyProductsSold(dailyOrderSummary);
            }
        }

    }

    /**
     * Method to calculate the daily client average expense
     * @param date the specific day to generate the daily report
     * @return the average of client daily expense
     */
    public Double calculateDailyClientAverageExpense(final Date date) {

        final AtomicReference<Double> average = new AtomicReference<>(0.0);
        final AtomicReference<Integer> totalDailyClients = new AtomicReference<>(0);
        if (date != null) {
            this.clients.stream()
                    .forEach(
                            client -> {
                                final List<Order> clientOrders = client.findOrdersByDateRange(date, date);
                                if (clientOrders != null && !clientOrders.isEmpty()) {
                                    //Gets the total expense by client
                                    final Double totalByClient = clientOrders.stream()
                                            .map(Order::calculateTotal)
                                            .reduce((a, b) -> a + b).orElse(0.0);

                                    //Adds the expense of the client to will calculate the average
                                    average.getAndSet(average.get() + totalByClient);
                                    //Adds the count of clients to will calculate the average
                                    totalDailyClients.getAndSet(totalDailyClients.get() + 1);
                                }
                            }
                    );

            //Calculate and set the average
            average.set(CoffeeShopUtils.formatDouble(average.get() / (double)totalDailyClients.get()));
        }

        return average.get();

    }

}
