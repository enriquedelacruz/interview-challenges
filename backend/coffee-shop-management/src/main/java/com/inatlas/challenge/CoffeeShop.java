package com.inatlas.challenge;

import com.inatlas.challenge.products.Menu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

}
