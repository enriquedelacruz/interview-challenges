package com.inatlas.challenge;

import com.inatlas.challenge.products.Menu;
import com.inatlas.challenge.utils.Utils;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class ClientTest {

    @Test
    public void testBasics() {

        Client client = new Client();
        assertThat(client.getOrders(), notNullValue());
        assertThat(client.getOrders().get(0).getDate(), notNullValue());

        client.getOrders().get(0).setDate(Utils.parseDate("21-02-2021", Utils.DATE_FORMAT));
        assertThat(Utils.formatDate(client.getOrders().get(0).getDate()), is("21-02-2021"));

    }

    @Test
    public void testClientOrders() {

        Client client = new Client();
        client.getOrders().get(0).setDate(Utils.parseDate("21-02-2021", Utils.DATE_FORMAT)); //To test different dates
        assertThat(Utils.formatDate(client.getOrders().get(0).getDate()), is("21-02-2021"));
        assertThat(client.getOrders(), notNullValue());

        Order firstOrder = client.getOrders().get(0);

        firstOrder.takeOrder(Menu.MenuProduct.ESPRESSO, 3);
        firstOrder.takeOrder(Menu.MenuProduct.LATTE, 2);
        Double total = firstOrder.printReceipt();
        assertThat(firstOrder.getTotal(), is(18.6)); //promo 1espresso x 2lattes applied

    }

    private Order buildOrder(String stringDate) {

        Order order = new Order();
        order.setDate(Utils.parseDate(stringDate, Utils.DATE_FORMAT)); //To test different dates
        order.takeOrder(Menu.MenuProduct.ESPRESSO, 3);
        order.takeOrder(Menu.MenuProduct.LATTE, 2);

        return order;

    }

    @Test
    public void testFindOrdersByDateRange() {

        Client client = new Client();
        List<Order> ordersFound = client.findOrdersByDateRange(null, null);
        assertThat(ordersFound, notNullValue());
        assertThat(ordersFound.size(), is(0));
        ordersFound = client.findOrdersByDateRange(null, Utils.parseDate("01-03-2021", Utils.DATE_FORMAT));
        assertThat(ordersFound, notNullValue());
        assertThat(ordersFound.size(), is(0));
        ordersFound = client.findOrdersByDateRange(Utils.parseDate("01-02-2021", Utils.DATE_FORMAT), null);
        assertThat(ordersFound, notNullValue());
        assertThat(ordersFound.size(), is(0));
        ordersFound = client.findOrdersByDateRange(Utils.parseDate("XXXXXX", Utils.DATE_FORMAT), Utils.parseDate("XXXX", Utils.DATE_FORMAT));
        assertThat(ordersFound, notNullValue());
        assertThat(ordersFound.size(), is(0));
        ordersFound = client.findOrdersByDateRange(Utils.parseDate("01/02/2021", Utils.DATE_FORMAT), Utils.parseDate("01/03/2021", Utils.DATE_FORMAT));
        assertThat(ordersFound, notNullValue());
        assertThat(ordersFound.size(), is(0));


        ordersFound = client.findOrdersByDateRange(Utils.parseDate("01-02-2021", Utils.DATE_FORMAT), Utils.parseDate("01-03-2021", Utils.DATE_FORMAT));
        assertThat(ordersFound, notNullValue());
        assertThat(ordersFound.size(), is(0));
        int ordersFoundCount = client.countOrdersByDateRange(Utils.parseDate("01-02-2021", Utils.DATE_FORMAT), Utils.parseDate("01-03-2021", Utils.DATE_FORMAT));
        assertThat(ordersFoundCount, is(0));

        client.getOrders().add(buildOrder("21-02-2021"));
        ordersFound = client.findOrdersByDateRange(Utils.parseDate("01-02-2021", Utils.DATE_FORMAT), Utils.parseDate("01-03-2021", Utils.DATE_FORMAT));
        assertThat(ordersFound, notNullValue());
        assertThat(ordersFound.size(), is(1));
        ordersFoundCount = client.countOrdersByDateRange(Utils.parseDate("01-02-2021", Utils.DATE_FORMAT), Utils.parseDate("01-03-2021", Utils.DATE_FORMAT));
        assertThat(ordersFoundCount, is(1));

        client.getOrders().add(buildOrder("21-03-2021"));
        ordersFound = client.findOrdersByDateRange(Utils.parseDate("01-02-2021", Utils.DATE_FORMAT), Utils.parseDate("01-03-2021", Utils.DATE_FORMAT));
        assertThat(ordersFound, notNullValue());
        assertThat(ordersFound.size(), is(1));
        ordersFoundCount = client.countOrdersByDateRange(Utils.parseDate("01-02-2021", Utils.DATE_FORMAT), Utils.parseDate("01-03-2021", Utils.DATE_FORMAT));
        assertThat(ordersFoundCount, is(1));

        client.getOrders().add(buildOrder("15-02-2021"));
        ordersFound = client.findOrdersByDateRange(Utils.parseDate("01-02-2021", Utils.DATE_FORMAT), Utils.parseDate("01-03-2021", Utils.DATE_FORMAT));
        assertThat(ordersFound, notNullValue());
        assertThat(ordersFound.size(), is(2));
        ordersFoundCount = client.countOrdersByDateRange(Utils.parseDate("01-02-2021", Utils.DATE_FORMAT), Utils.parseDate("01-03-2021", Utils.DATE_FORMAT));
        assertThat(ordersFoundCount, is(2));

    }

}
