package com.inatlas.challenge;

import com.inatlas.challenge.products.CoffeeShopMenu;
import com.inatlas.challenge.utils.CoffeeShopUtils;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class OrderTest {

    private final static String dateFebr21st = "21-02-2021";

    @Test
    public void testPrintMenu() {
        CoffeeShopMenu.printMenu();
        //Nothing to assert
        assertThat(CoffeeShopMenu.MenuProduct.values(), notNullValue());
    }

    @Test
    public void testTakeMyFirstOrder() {
        final Order order = new Order();
        CoffeeShopMenu.printMenu();
        order.takeOrder(CoffeeShopMenu.MenuProduct.LATTE, 1);
        order.takeOrder(CoffeeShopMenu.MenuProduct.ESPRESSO, 1);
        order.takeOrder(CoffeeShopMenu.MenuProduct.SANDWICH, 1);
        Double total = order.printReceipt();
        // Total should be $19.4 ( 5.3 + 4 + 10.10 = 19.4 )
        assertThat(total, is(19.4));

        order.takeOrder(CoffeeShopMenu.MenuProduct.ESPRESSO, 1);
        total = order.printReceipt();
        // Total should be $23.4 ( 5.3 + 4 + 10.10 + 4 = 23.4 )
        assertThat(total, is(23.4));

        order.takeOrder(CoffeeShopMenu.MenuProduct.SANDWICH, 1);
        total = order.printReceipt();
        // Total should be $33.5 ( 5.3 + 4 + 10.10 + 4 + 10.10 + 10.10 )
        assertThat(total, is(33.5));

        order.takeOrder(CoffeeShopMenu.MenuProduct.SANDWICH, 2);
        total = order.printReceipt();
        // Total should be $53.7 ( 5.3 + 4 + 10.10 + 4 + 10.10 + 10.10 + 10.10 = 53.7 )
        // Total is greater than $50, so we apply discount of $3 Latte, total should be $51.4 (the cheapest)
        assertThat(total, is(51.4));
    }

    @Test
    public void testTakeMySecondOrder() {
        final Order order = new Order();
        CoffeeShopMenu.printMenu();
        order.takeOrder(CoffeeShopMenu.MenuProduct.LATTE, 2);
        order.takeOrder(CoffeeShopMenu.MenuProduct.ESPRESSO, 1);
        order.takeOrder(CoffeeShopMenu.MenuProduct.SANDWICH, 1);
        final Double total = order.printReceipt();
        // Total should be $20.7 ( 5.3 + 5.3 + 4 + 10.10 - 4 [promotion] = 20.7 )
        assertThat(total, is(20.7));
    }

    @Test
    public void testTakeMyThirdOrder3() {
        final Order order = new Order();
        CoffeeShopMenu.printMenu();
        order.takeOrder(CoffeeShopMenu.MenuProduct.LATTE, 1);
        order.takeOrder(CoffeeShopMenu.MenuProduct.LATTE, 1);
        order.takeOrder(CoffeeShopMenu.MenuProduct.ESPRESSO, 1);
        order.takeOrder(CoffeeShopMenu.MenuProduct.SANDWICH, 1);
        final Double total = order.printReceipt();
        // Total should be $20.7 ( 5.3 + 5.3 + 4 + 10.10 - 4 [promotion] = 20.7 )
        assertThat(total, is(20.7));
    }

    @Test
    public void testTakeMyFourthOrder() {
        final Order order = new Order();
        CoffeeShopMenu.printMenu();
        order.takeOrder(CoffeeShopMenu.MenuProduct.LATTE, 2);
        order.takeOrder(CoffeeShopMenu.MenuProduct.ESPRESSO, 1);
        order.takeOrder(CoffeeShopMenu.MenuProduct.ESPRESSO, 1);
        order.takeOrder(CoffeeShopMenu.MenuProduct.SANDWICH, 1);
        Double total = order.printReceipt();
        // Total should be $24.7 ( 5.3 + 5.3 + 4 + 4 + 10.10 - 4 [promotion for 2 lattes] = 24.7 )
        assertThat(total, is(24.7));

        order.takeOrder(CoffeeShopMenu.MenuProduct.LATTE, 1); //Now we have 3 lattes and we expect 1 free espresso
        total = order.printReceipt();
        // Total should be $30.0 ( 5.3 + 5.3 + 4 + 4 + 10.10 + 5.3 - 4 [promotion for 2 lattes] = 30.0 )
        assertThat(total, is(30.0));

        order.takeOrder(CoffeeShopMenu.MenuProduct.LATTE, 1); //Now we have 3 lattes and we expect 2 free espressos
        total = order.printReceipt();
        // Total should be $31.3 ( 5.3 + 5.3 + 4 + 4 + 10.10 + 5.3 + 5.3 - 8 [promotion for 4 lattes] = 31.3 )
        assertThat(total, is(31.3));

        order.takeOrder(CoffeeShopMenu.MenuProduct.ESPRESSO, 1); //Now we have 1 espresso more, but this won't be free
        total = order.printReceipt();
        // Total should be $35.3 ( 5.3 + 5.3 + 4 + 4 + 10.10 + 5.3 + 5.3 + 4 - 8 [promotion for 4 lattes] = 35.3 )
        assertThat(total, is(35.3));
    }

    @Test
    public void testPromotions() {
        final Order order = new Order();
        CoffeeShopMenu.printMenu();

        order.takeOrder(CoffeeShopMenu.MenuProduct.LATTE, 2);
        order.takeOrder(CoffeeShopMenu.MenuProduct.ESPRESSO, 1);
        order.takeOrder(CoffeeShopMenu.MenuProduct.ESPRESSO, 1);
        order.takeOrder(CoffeeShopMenu.MenuProduct.SANDWICH, 6);
        // Total of order should be $59.0 ( 5.3 + 5.3 + 4 + 4 + 10.10 x6 = 79.2 )
        // Total after discount of 1espressox2lattes should be $75.2
        // Total after discount of 5% should be $75.24
        // Total after discount of $3 Latte should be $74.6 (the cheapest)
        Double total = order.printReceipt();
        assertThat(total, is(74.6));

        order.takeOrder(CoffeeShopMenu.MenuProduct.SANDWICH, 4);
        // Total of order should be $119.6 ( 5.3 + 5.3 + 4 + 4 + 10.10 x10 = 119.6 )
        // Total after discount of 1espressox2lattes should be $115.6
        // Total after discount of 5% should be $113.62 (the cheapest)
        total = order.printReceipt();
        assertThat(total, is(113.62));
    }

    @Test
    public void testNewProducts() {
        final Order order = new Order();
        CoffeeShopMenu.printMenu();

        order.takeOrder(CoffeeShopMenu.MenuProduct.CAPPUCCINO, 1);
        order.takeOrder(CoffeeShopMenu.MenuProduct.TEA, 1);
        order.takeOrder(CoffeeShopMenu.MenuProduct.CAKE_SLICE, 1);
        order.takeOrder(CoffeeShopMenu.MenuProduct.MILK, 1);

        Double total = order.printReceipt();
        // Total should be $24.1 ( 8.0 + 6.1 + 9.0 + 1.0 = 24.1 )
        assertThat(total, is(24.1));

        order.takeOrder(CoffeeShopMenu.MenuProduct.CAPPUCCINO, 5);
        total = order.printReceipt();
        // Total should be $60.89 ( 8.0 + 6.1 + 9.0 + 1.0 + 8.0*5 = 64.1 - 5% )
        assertThat(total, is(60.89));

    }

    @Test
    public void testDates() {

        final Order order = new Order(); //The constructor assign the
        order.setDate(CoffeeShopUtils.parseDate(dateFebr21st, CoffeeShopUtils.DATE_FORMAT)); //To test different dates
        assertThat(CoffeeShopUtils.formatDate(order.getDate()), is(dateFebr21st));

        order.setDate(CoffeeShopUtils.parseDate(dateFebr21st, CoffeeShopUtils.DATE_FORMAT)); //Force a specific date fot testing
        assertThat(CoffeeShopUtils.formatDate(order.getDate()), is(dateFebr21st));

    }

}