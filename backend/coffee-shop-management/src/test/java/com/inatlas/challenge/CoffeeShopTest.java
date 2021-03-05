package com.inatlas.challenge;

import com.inatlas.challenge.products.CoffeeShopMenu;
import com.inatlas.challenge.utils.CoffeeShopUtils;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Date;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CoffeeShopTest {

    private final static String today = "04-03-2021";
    private final static String dateMarch3rd = "03-03-2021";

    @Test
    public void test1CoffeeShopBasicTests() {

        assertThat(CoffeeShop.getInstance(), notNullValue());
        //Test the singleton class, getInstance always returns the same object
        assertThat(CoffeeShop.getInstance().toString(), is(CoffeeShop.getInstance().toString()));

    }

    @Test
    public void test2CoffeeShopTests() {

        CoffeeShop.getInstance().printMenu(); //This should prints the whole menu
        assertThat(CoffeeShop.getInstance().getClients(), notNullValue());

        CoffeeShop.getInstance().registerNewClient();
        assertThat(CoffeeShop.getInstance().getClients(), notNullValue());
        assertThat(CoffeeShop.getInstance().getClients().size(), is(1));

        CoffeeShop.getInstance().registerNewClient();
        assertThat(CoffeeShop.getInstance().getClients(), notNullValue());
        assertThat(CoffeeShop.getInstance().getClients().size(), is(2));

        final Client client1 = CoffeeShop.getInstance().getClients().get(0);
        assertThat(client1.getOrders(), notNullValue());
        final Client client2 = CoffeeShop.getInstance().getClients().get(1);
        assertThat(client2.getOrders(), notNullValue());

        //Create first order in first client
        final Order order1client1 = client1.getOrders().get(0);
        order1client1.setDate(CoffeeShopUtils.parseDate("01-02-2021", CoffeeShopUtils.DATE_FORMAT));
        order1client1.takeOrder(CoffeeShopMenu.MenuProduct.ESPRESSO, 1);
        order1client1.takeOrder(CoffeeShopMenu.MenuProduct.LATTE, 1);
        order1client1.takeOrder(CoffeeShopMenu.MenuProduct.SANDWICH, 1);

        //Defines range of search
        final Date beginDate = CoffeeShopUtils.parseDate("01-02-2021", CoffeeShopUtils.DATE_FORMAT);
        final Date endDate = CoffeeShopUtils.parseDate("01-03-2021", CoffeeShopUtils.DATE_FORMAT);

        int countClients = client1.countOrdersByDateRange(beginDate, endDate);
        assertThat(countClients, is(1));

        //Create first order in second client
        final Order order1client2 = client2.getOrders().get(0);
        order1client2.setDate(CoffeeShopUtils.parseDate("20-02-2021", CoffeeShopUtils.DATE_FORMAT));
        order1client2.takeOrder(CoffeeShopMenu.MenuProduct.ESPRESSO, 5);
        order1client2.takeOrder(CoffeeShopMenu.MenuProduct.TEA, 1);
        order1client2.takeOrder(CoffeeShopMenu.MenuProduct.CAKE_SLICE, 5);

        countClients = client2.countOrdersByDateRange(beginDate, endDate);
        assertThat(countClients, is(1));

        //Count all the orders in the shop into the date range
        countClients = CoffeeShop.getInstance().countClientsByDateRange(beginDate, endDate);
        assertThat(countClients, is(2));

        //Create a new order for client2
        final Order order2client2 = client2.registerNewOrder();
        order2client2.setDate(CoffeeShopUtils.parseDate("20-02-2021", CoffeeShopUtils.DATE_FORMAT));
        order2client2.takeOrder(CoffeeShopMenu.MenuProduct.LATTE, 4);
        order2client2.takeOrder(CoffeeShopMenu.MenuProduct.TEA, 1);
        order2client2.takeOrder(CoffeeShopMenu.MenuProduct.CAPPUCCINO, 4);

        countClients = client2.countOrdersByDateRange(beginDate, endDate);
        assertThat(countClients, is(2));

        //Count all the orders in the shop into the date range
        countClients = CoffeeShop.getInstance().countClientsByDateRange(beginDate, endDate);
        assertThat(countClients, is(3));

    }

    private Order buildOrder(final String stringDate) {

        final Order order = new Order();
        order.setDate(CoffeeShopUtils.parseDate(stringDate, CoffeeShopUtils.DATE_FORMAT)); //To test different dates
        order.takeOrder(CoffeeShopMenu.MenuProduct.ESPRESSO, 3);
        order.takeOrder(CoffeeShopMenu.MenuProduct.LATTE, 2);

        return order;

    }

    private Client buildClient(final String stringDate) {

        final Client client = new Client();
        client.getOrders().set(0, buildOrder(stringDate));

        return client;

    }

    @Test
    public void test3CoffeeShopDailyProductsSold() {

        Order dailyOrderSummary = CoffeeShop.getInstance().listDailyProductsSold(CoffeeShopUtils.parseDate(null, CoffeeShopUtils.DATE_FORMAT));
        Printer.getInstance().printDailyProductsSold(dailyOrderSummary);

        for (int i = 0; i < 10; i++) {
            CoffeeShop.getInstance().getClients().add(buildClient(today));
        }
        final Date todayDate = CoffeeShopUtils.parseDate(today, CoffeeShopUtils.DATE_FORMAT);

        dailyOrderSummary = CoffeeShop.getInstance().listDailyProductsSold(todayDate);
        assertThat(dailyOrderSummary.getTotal(), is(226.0));
        CoffeeShop.getInstance().printDailyProductsSold(todayDate);
        //This should print a receipt with total products sold in today

        dailyOrderSummary = CoffeeShop.getInstance().listDailyProductsSold(CoffeeShopUtils.parseDate("15-02-2021", CoffeeShopUtils.DATE_FORMAT));
        assertThat(dailyOrderSummary.getTotal(), is(0.0));
        CoffeeShop.getInstance().printDailyProductsSold(CoffeeShopUtils.parseDate("15-02-2021", CoffeeShopUtils.DATE_FORMAT));
        //This should print a receipt with 0 products sold

    }

    @Test
    public void test4CoffeeShopDailyAverage() {

        Double dailyAverageExpense = CoffeeShop.getInstance().calculateDailyClientAverageExpense(null);
        assertThat(dailyAverageExpense, is(0.0));

        for (int i = 0; i < 10; i++) {
            CoffeeShop.getInstance().getClients().add(buildClient(dateMarch3rd));
        }
        final Date todayDate = CoffeeShopUtils.parseDate(dateMarch3rd, CoffeeShopUtils.DATE_FORMAT);

        //We have 10 clients with 1 order of 18.6 each client
        dailyAverageExpense = CoffeeShop.getInstance().calculateDailyClientAverageExpense(todayDate);
        assertThat(dailyAverageExpense, is(18.6));

        //Adds new 10 orders with other date, this should not change the daily average
        for (int i=0; i<10; i++) {
            CoffeeShop.getInstance().getClients().add(buildClient("05-03-2021"));
        }

        //We still have average of 18.6
        dailyAverageExpense = CoffeeShop.getInstance().calculateDailyClientAverageExpense(todayDate);
        assertThat(dailyAverageExpense, is(18.6));

        //Adds an order to change the daily average
        final Client lastClient = new Client();
        lastClient.getOrders().get(0).setDate(todayDate);
        lastClient.getOrders().get(0).takeOrder(CoffeeShopMenu.MenuProduct.CAKE_SLICE, 10);
        lastClient.getOrders().get(0).takeOrder(CoffeeShopMenu.MenuProduct.TEA, 5);
        CoffeeShop.getInstance().getClients().add(lastClient);

        //10 clients with 18.6 order each client  +  1 client with a 120.5 order ($9x10 + $6.1x5) - 5% promo = 300.48 / 11 clients = 27.32 average
        dailyAverageExpense = CoffeeShop.getInstance().calculateDailyClientAverageExpense(todayDate);
        assertThat(dailyAverageExpense, is(27.32));

    }

}