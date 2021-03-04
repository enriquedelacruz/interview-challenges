package com.inatlas.challenge;

import com.inatlas.challenge.products.Menu;
import com.inatlas.challenge.utils.Utils;
import org.junit.Test;

import java.util.Date;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class CoffeeShopTest {

    @Test
    public void coffeeShopBasicTests() {

        assertThat(CoffeeShop.getInstance(), notNullValue());
        //Test the singleton class, getInstance always returns the same object
        assertThat(CoffeeShop.getInstance().toString(), is(CoffeeShop.getInstance().toString()));

    }

    @Test
    public void coffeeShopTests() {

        CoffeeShop.getInstance().printMenu(); //This should prints the whole menu
        assertThat(CoffeeShop.getInstance().getClients(), notNullValue());

        CoffeeShop.getInstance().registerNewClient();
        assertThat(CoffeeShop.getInstance().getClients(), notNullValue());
        assertThat(CoffeeShop.getInstance().getClients().size(), is(1));

        CoffeeShop.getInstance().registerNewClient();
        assertThat(CoffeeShop.getInstance().getClients(), notNullValue());
        assertThat(CoffeeShop.getInstance().getClients().size(), is(2));

        Client client1 = CoffeeShop.getInstance().getClients().get(0);
        assertThat(client1.getOrders(), notNullValue());
        Client client2 = CoffeeShop.getInstance().getClients().get(1);
        assertThat(client2.getOrders(), notNullValue());

        //Create first order in first client
        Order order1client1 = client1.getOrders().get(0);
        order1client1.setDate(Utils.parseDate("01-02-2021", Utils.DATE_FORMAT));
        order1client1.takeOrder(Menu.MenuProduct.ESPRESSO, 1);
        order1client1.takeOrder(Menu.MenuProduct.LATTE, 1);
        order1client1.takeOrder(Menu.MenuProduct.SANDWICH, 1);

        //Defines range of search
        Date beginDate = Utils.parseDate("01-02-2021", Utils.DATE_FORMAT);
        Date endDate = Utils.parseDate("01-03-2021", Utils.DATE_FORMAT);

        int countClients = client1.countOrdersByDateRange(beginDate, endDate);
        assertThat(countClients, is(1));

        //Create first order in second client
        Order order1client2 = client2.getOrders().get(0);
        order1client2.setDate(Utils.parseDate("20-02-2021", Utils.DATE_FORMAT));
        order1client2.takeOrder(Menu.MenuProduct.ESPRESSO, 5);
        order1client2.takeOrder(Menu.MenuProduct.TEA, 1);
        order1client2.takeOrder(Menu.MenuProduct.CAKE_SLICE, 5);

        countClients = client2.countOrdersByDateRange(beginDate, endDate);
        assertThat(countClients, is(1));

        //Count all the orders in the shop into the date range
        countClients = CoffeeShop.getInstance().countClientsByDateRange(beginDate, endDate);
        assertThat(countClients, is(2));

        //Create a new order for client2
        Order order2client2 = client2.registerNewOrder();
        order2client2.setDate(Utils.parseDate("20-02-2021", Utils.DATE_FORMAT));
        order2client2.takeOrder(Menu.MenuProduct.LATTE, 4);
        order2client2.takeOrder(Menu.MenuProduct.TEA, 1);
        order2client2.takeOrder(Menu.MenuProduct.CAPPUCCINO, 4);

        countClients = client2.countOrdersByDateRange(beginDate, endDate);
        assertThat(countClients, is(2));

        //Count all the orders in the shop into the date range
        countClients = CoffeeShop.getInstance().countClientsByDateRange(beginDate, endDate);
        assertThat(countClients, is(3));

    }

    private Order buildOrder(String stringDate) {

        Order order = new Order();
        order.setDate(Utils.parseDate(stringDate, Utils.DATE_FORMAT)); //To test different dates
        order.takeOrder(Menu.MenuProduct.ESPRESSO, 3);
        order.takeOrder(Menu.MenuProduct.LATTE, 2);

        return order;

    }

    private Client buildClient(String stringDate) {

        Client client = new Client();
        client.getOrders().set(0, buildOrder(stringDate));

        return client;

    }

    @Test
    public void testCoffeeShopReports() {

        Order dailyOrderSummary = CoffeeShop.getInstance().listDailyProductsSold(Utils.parseDate(null, Utils.DATE_FORMAT));
        Printer.getInstance().printDailyProductsSold(dailyOrderSummary);

        final String TODAY = "04-03-2021";
        for (int i=0; i<10; i++) {
            CoffeeShop.getInstance().getClients().add(buildClient(TODAY));
        }
        final Date todayDate = Utils.parseDate(TODAY, Utils.DATE_FORMAT);

        dailyOrderSummary = CoffeeShop.getInstance().listDailyProductsSold(todayDate);
        assertThat(dailyOrderSummary.getTotal(), is(226.0));
        CoffeeShop.getInstance().printDailyProductsSold(todayDate);
        //This should print a receipt with total products sold in TODAY

        dailyOrderSummary = CoffeeShop.getInstance().listDailyProductsSold(Utils.parseDate("01-02-2021", Utils.DATE_FORMAT));
        assertThat(dailyOrderSummary.getTotal(), is(0.0));
        CoffeeShop.getInstance().printDailyProductsSold(Utils.parseDate("01-02-2021", Utils.DATE_FORMAT));
        //This should print a receipt with 0 products sold

    }

}