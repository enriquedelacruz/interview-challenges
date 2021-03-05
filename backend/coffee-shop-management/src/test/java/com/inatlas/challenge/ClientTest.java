package com.inatlas.challenge;

import com.inatlas.challenge.products.CoffeeShopMenu;
import com.inatlas.challenge.utils.CoffeeShopUtils;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class ClientTest {

    private final static String stringDate = "21-02-2021";
    private final static String dateMarch1st = "01-03-2021";
    private final static String dateFebr1st = "01-02-2021";

    @Test
    public void testBasics() {

        final Client client = new Client();
        assertThat(client.getOrders(), notNullValue());
        assertThat(client.getOrders().get(0).getDate(), notNullValue());

        client.getOrders().get(0).setDate(CoffeeShopUtils.parseDate(stringDate, CoffeeShopUtils.DATE_FORMAT));
        assertThat(CoffeeShopUtils.formatDate(client.getOrders().get(0).getDate()), is(stringDate));

    }

    @Test
    public void testClientOrders() {

        final Client client = new Client();
        client.getOrders().get(0).setDate(CoffeeShopUtils.parseDate("21-02-2021", CoffeeShopUtils.DATE_FORMAT)); //To test different dates
        assertThat(CoffeeShopUtils.formatDate(client.getOrders().get(0).getDate()), is("21-02-2021"));
        assertThat(client.getOrders(), notNullValue());

        final Order firstOrder = client.getOrders().get(0);

        firstOrder.takeOrder(CoffeeShopMenu.MenuProduct.ESPRESSO, 3);
        firstOrder.takeOrder(CoffeeShopMenu.MenuProduct.LATTE, 2);
        firstOrder.printReceipt();
        assertThat(firstOrder.getTotal(), is(18.6)); //promo 1espresso x 2lattes applied

    }

    private Order buildOrder(final String stringDate) {

        final Order order = new Order();
        order.setDate(CoffeeShopUtils.parseDate(stringDate, CoffeeShopUtils.DATE_FORMAT)); //To test different dates
        order.takeOrder(CoffeeShopMenu.MenuProduct.ESPRESSO, 3);
        order.takeOrder(CoffeeShopMenu.MenuProduct.LATTE, 2);

        return order;

    }

    @Test
    public void testFindOrdersByDateRange() {

        final Client client = new Client();
        List<Order> ordersFound = client.findOrdersByDateRange(null, null);
        assertThat(ordersFound, notNullValue());
        assertThat(ordersFound.size(), is(0));
        ordersFound = client.findOrdersByDateRange(null, CoffeeShopUtils.parseDate(dateMarch1st, CoffeeShopUtils.DATE_FORMAT));
        assertThat(ordersFound, notNullValue());
        assertThat(ordersFound.size(), is(0));
        ordersFound = client.findOrdersByDateRange(CoffeeShopUtils.parseDate(dateFebr1st, CoffeeShopUtils.DATE_FORMAT), null);
        assertThat(ordersFound, notNullValue());
        assertThat(ordersFound.size(), is(0));
        ordersFound = client.findOrdersByDateRange(CoffeeShopUtils.parseDate("XXXXXX", CoffeeShopUtils.DATE_FORMAT), CoffeeShopUtils.parseDate("XXXX", CoffeeShopUtils.DATE_FORMAT));
        assertThat(ordersFound, notNullValue());
        assertThat(ordersFound.size(), is(0));
        ordersFound = client.findOrdersByDateRange(CoffeeShopUtils.parseDate("01/02/2021", CoffeeShopUtils.DATE_FORMAT), CoffeeShopUtils.parseDate("01/03/2021", CoffeeShopUtils.DATE_FORMAT));
        assertThat(ordersFound, notNullValue());
        assertThat(ordersFound.size(), is(0));


        ordersFound = client.findOrdersByDateRange(CoffeeShopUtils.parseDate(dateFebr1st, CoffeeShopUtils.DATE_FORMAT), CoffeeShopUtils.parseDate(dateMarch1st, CoffeeShopUtils.DATE_FORMAT));
        assertThat(ordersFound, notNullValue());
        assertThat(ordersFound.size(), is(0));
        int ordersFoundCount = client.countOrdersByDateRange(CoffeeShopUtils.parseDate(dateFebr1st, CoffeeShopUtils.DATE_FORMAT), CoffeeShopUtils.parseDate(dateMarch1st, CoffeeShopUtils.DATE_FORMAT));
        assertThat(ordersFoundCount, is(0));

        client.getOrders().add(buildOrder("21-02-2021"));
        ordersFound = client.findOrdersByDateRange(CoffeeShopUtils.parseDate(dateFebr1st, CoffeeShopUtils.DATE_FORMAT), CoffeeShopUtils.parseDate(dateMarch1st, CoffeeShopUtils.DATE_FORMAT));
        assertThat(ordersFound, notNullValue());
        assertThat(ordersFound.size(), is(1));
        ordersFoundCount = client.countOrdersByDateRange(CoffeeShopUtils.parseDate(dateFebr1st, CoffeeShopUtils.DATE_FORMAT), CoffeeShopUtils.parseDate(dateMarch1st, CoffeeShopUtils.DATE_FORMAT));
        assertThat(ordersFoundCount, is(1));

        client.getOrders().add(buildOrder("21-03-2021"));
        ordersFound = client.findOrdersByDateRange(CoffeeShopUtils.parseDate(dateFebr1st, CoffeeShopUtils.DATE_FORMAT), CoffeeShopUtils.parseDate(dateMarch1st, CoffeeShopUtils.DATE_FORMAT));
        assertThat(ordersFound, notNullValue());
        assertThat(ordersFound.size(), is(1));
        ordersFoundCount = client.countOrdersByDateRange(CoffeeShopUtils.parseDate(dateFebr1st, CoffeeShopUtils.DATE_FORMAT), CoffeeShopUtils.parseDate(dateMarch1st, CoffeeShopUtils.DATE_FORMAT));
        assertThat(ordersFoundCount, is(1));

        client.getOrders().add(buildOrder("15-02-2021"));
        ordersFound = client.findOrdersByDateRange(CoffeeShopUtils.parseDate(dateFebr1st, CoffeeShopUtils.DATE_FORMAT), CoffeeShopUtils.parseDate(dateMarch1st, CoffeeShopUtils.DATE_FORMAT));
        assertThat(ordersFound, notNullValue());
        assertThat(ordersFound.size(), is(2));
        ordersFoundCount = client.countOrdersByDateRange(CoffeeShopUtils.parseDate(dateFebr1st, CoffeeShopUtils.DATE_FORMAT), CoffeeShopUtils.parseDate(dateMarch1st, CoffeeShopUtils.DATE_FORMAT));
        assertThat(ordersFoundCount, is(2));

    }

}
