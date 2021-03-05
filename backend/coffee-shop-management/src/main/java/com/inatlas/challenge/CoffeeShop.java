package com.inatlas.challenge;

import com.inatlas.challenge.products.CoffeeShopMenu;
import com.inatlas.challenge.products.Product;
import com.inatlas.challenge.utils.CoffeeShopUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

//Singleton class for CoffeeShop
public class CoffeeShop {

    private static CoffeeShop coffeeShopInstance;
    private List<Client> clients;

    //Constructors
    public CoffeeShop() {
        this.clients = new ArrayList<>();
    }

    public CoffeeShop(final List<Client> clients) {
        this.clients = clients;
    }

    //Getters and Setters
    public static synchronized CoffeeShop getInstance() {
        if (coffeeShopInstance == null) {
            coffeeShopInstance = new CoffeeShop();
        }
        return coffeeShopInstance;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(final List<Client> clients) {
        this.clients = clients;
    }

    //Public methods
    public void printMenu() {
        CoffeeShopMenu.printMenu();
    }

    public void registerNewClient() {
        clients.add(new Client());
    }

    public int countClientsByDateRange(final Date beginDate, final Date endDate) {

        int count = 0;
        if (beginDate != null && endDate != null) {
            count = this.clients.stream()
                    .map(c -> c.countOrdersByDateRange(beginDate, endDate))
                    .reduce(count, (a, b) -> a + b);
        }

        return count;

    }

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
                                        .filter(p -> p.getName() == mp)
                                        .map(Product::getQuantity)
                                        .reduce((a,b) -> a + b).orElse(0);

                                dailyProducts.add(new Product(mp, productQuantity));
                            }
                    );
        }
        dailyOrderSummary.calculateTotalWithoutPromotions();

        return dailyOrderSummary;

    }

    public void printDailyProductsSold(final Date day) {

        if (day != null) {
            final Order dailyOrderSummary = listDailyProductsSold(day);
            if (dailyOrderSummary != null) {
                Printer.getInstance().printDailyProductsSold(dailyOrderSummary);
            }
        }

    }

    public Double calculateDailyClientAverageExpense(final Date date) {

        final AtomicReference<Double> average = new AtomicReference<>(0.0);
        final AtomicReference<Integer> totalDailyClients = new AtomicReference<>(0);
        if (date != null) {
            this.clients.stream()
                    .forEach(
                            client -> {
                                final List<Order> clientOrders = client.findOrdersByDateRange(date, date);
                                if (clientOrders != null && !clientOrders.isEmpty()) {
                                    final Double totalByClient = clientOrders.stream()
                                            .map(Order::calculateTotal)
                                            .reduce((a, b) -> a + b).orElse(0.0);

                                    average.getAndSet(average.get() + totalByClient);
                                    totalDailyClients.getAndSet(totalDailyClients.get() + 1);
                                }
                            }
                    );

            average.set(CoffeeShopUtils.formatDouble(average.get() / (double)totalDailyClients.get()));
        }

        return average.get();

    }

}
