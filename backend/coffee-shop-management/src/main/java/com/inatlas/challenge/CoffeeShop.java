package com.inatlas.challenge;

import com.inatlas.challenge.products.Menu;
import com.inatlas.challenge.products.Product;

import java.util.*;
import java.util.stream.Collectors;

//Singleton class for CoffeeShop
public class CoffeeShop {

    private static CoffeeShop coffeeShop;
    private List<Client> clients;

    //Constructors
    public CoffeeShop() {
        this.clients = new ArrayList<>();
    }

    public CoffeeShop(List<Client> clients) {
        this.clients = clients;
    }

    //Getters and Setters
    synchronized public static CoffeeShop getInstance() {
        if (coffeeShop == null) {
            coffeeShop = new CoffeeShop();
        }
        return coffeeShop;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    //Public methods
    public void printMenu() {
        Menu.printMenu();
    }

    public void registerNewClient() {
        clients.add(new Client());
    }

    public int countClientsByDateRange(Date beginDate, Date endDate) {

        int count = 0;
        if (beginDate != null && endDate != null) {
            count = this.clients.stream()
                    .map(c -> c.countOrdersByDateRange(beginDate, endDate))
                    .reduce(count, (a, b) -> a + b);
        }

        return count;

    }

    public Order listDailyProductsSold(Date day) {

        //First gets the order list of the day
        List<Order> dailyOrders = null;
        if (day != null) {
            dailyOrders = this.clients.stream()
                    .map(c -> c.findOrdersByDateRange(day, day))
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
        }

        //Second, create an order to put all the products in the menu
        Order dailyOrderSummary = new Order();
        dailyOrderSummary.setDate(day);
        List<Product> dailyProducts = dailyOrderSummary.getProducts();
        if (dailyOrders != null && !dailyOrders.isEmpty()) {
            List<Product> allDailyProducts = dailyOrders.stream()
                    .map(Order::getProducts)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());

            //For each menu product, sums the quantity of these products that are been sold
            Arrays.stream(Menu.MenuProduct.values())
                    .forEach(
                            mp -> {
                                int productQuantity = allDailyProducts.stream()
                                        .filter(p -> p.getName() == mp)
                                        .map(p -> p.getQuantity())
                                        .reduce((a,b) -> a + b).orElse(0);

                                dailyProducts.add(new Product(mp, productQuantity));
                            }
                    );
        } else {
            //For each menu product adds a product
            Arrays.stream(Menu.MenuProduct.values())
                    .forEach(
                            mp -> {
                                dailyProducts.add(new Product(mp, 0));
                            }
                    );
        }
        dailyOrderSummary.calculateTotalWithoutPromotions();

        return dailyOrderSummary;

    }

    public void printDailyProductsSold(Date day) {
        if (day != null) {
            Order dailyOrderSummary = listDailyProductsSold(day);
            if (dailyOrderSummary != null) {
                Printer.getInstance().printDailyProductsSold(dailyOrderSummary);
            }
        }
    }

}
