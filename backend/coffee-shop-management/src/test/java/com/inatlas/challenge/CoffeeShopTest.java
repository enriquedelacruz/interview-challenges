package com.inatlas.challenge;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CoffeeShopTest {

    @Test
    public void testPrintMenu() {
        CoffeeShop coffeeShop = new CoffeeShop();
        coffeeShop.printMenu();
    }

    @Test
    public void testTakeMyFirstOrder() {
        CoffeeShop coffeeShop = new CoffeeShop();
        coffeeShop.printMenu();
        coffeeShop.takeOrder(Menu.LATTE, 1);
        coffeeShop.takeOrder(Menu.ESPRESSO, 1);
        coffeeShop.takeOrder(Menu.SANDWICH, 1);
        Double total = coffeeShop.printReceipt();
        // Total should be $19.4 ( 5.3 + 4 + 10.10 = 19.4 )
        assertThat(total, is(19.4));

        coffeeShop.takeOrder(Menu.ESPRESSO, 1);
        total = coffeeShop.printReceipt();
        // Total should be $23.4 ( 5.3 + 4 + 10.10 + 4 = 23.4 )
        assertThat(total, is(23.4));

        coffeeShop.takeOrder(Menu.SANDWICH, 1);
        total = coffeeShop.printReceipt();
        // Total should be $33.5 ( 5.3 + 4 + 10.10 + 4 + 10.10 + 10.10 )
        assertThat(total, is(33.5));

        coffeeShop.takeOrder(Menu.SANDWICH, 2);
        total = coffeeShop.printReceipt();
        // Total should be $53.7 ( 5.3 + 4 + 10.10 + 4 + 10.10 + 10.10 + 10.10 = 53.7 )
        assertThat(total, is(53.7));
    }

    @Test
    public void testTakeMySecondOrder() {
        CoffeeShop coffeeShop = new CoffeeShop();
        coffeeShop.printMenu();
        coffeeShop.takeOrder(Menu.LATTE, 2);
        coffeeShop.takeOrder(Menu.ESPRESSO, 1);
        coffeeShop.takeOrder(Menu.SANDWICH, 1);
        Double total = coffeeShop.printReceipt();
        // Total should be $20.7 ( 5.3 + 5.3 + 4 + 10.10 - 4 [promotion] = 20.7 )
        assertThat(total, is(20.7));
    }

    @Test
    public void testTakeMyThirdOrder3() {
        CoffeeShop coffeeShop = new CoffeeShop();
        coffeeShop.printMenu();
        coffeeShop.takeOrder(Menu.LATTE, 1);
        coffeeShop.takeOrder(Menu.LATTE, 1);
        coffeeShop.takeOrder(Menu.ESPRESSO, 1);
        coffeeShop.takeOrder(Menu.SANDWICH, 1);
        Double total = coffeeShop.printReceipt();
        // Total should be $20.7 ( 5.3 + 5.3 + 4 + 10.10 - 4 [promotion] = 20.7 )
        assertThat(total, is(20.7));
    }

    @Test
    public void testTakeMyFourthOrder() {
        CoffeeShop coffeeShop = new CoffeeShop();
        coffeeShop.printMenu();
        coffeeShop.takeOrder(Menu.LATTE, 2);
        coffeeShop.takeOrder(Menu.ESPRESSO, 1);
        coffeeShop.takeOrder(Menu.ESPRESSO, 1);
        coffeeShop.takeOrder(Menu.SANDWICH, 1);
        Double total = coffeeShop.printReceipt();
        // Total should be $24.7 ( 5.3 + 5.3 + 4 + 4 + 10.10 - 4 [promotion for 2 lattes] = 24.7 )
        assertThat(total, is(24.7));

        coffeeShop.takeOrder(Menu.LATTE, 1); //Now we have 3 lattes and we expect 1 free espresso
        total = coffeeShop.printReceipt();
        // Total should be $30.0 ( 5.3 + 5.3 + 4 + 4 + 10.10 + 5.3 - 4 [promotion for 2 lattes] = 30.0 )
        assertThat(total, is(30.0));

        coffeeShop.takeOrder(Menu.LATTE, 1); //Now we have 3 lattes and we expect 2 free espressos
        total = coffeeShop.printReceipt();
        // Total should be $31.3 ( 5.3 + 5.3 + 4 + 4 + 10.10 + 5.3 + 5.3 - 8 [promotion for 4 lattes] = 31.3 )
        assertThat(total, is(31.3));

        coffeeShop.takeOrder(Menu.ESPRESSO, 1); //Now we have 1 espresso more, but this won't be free
        total = coffeeShop.printReceipt();
        // Total should be $35.3 ( 5.3 + 5.3 + 4 + 4 + 10.10 + 5.3 + 5.3 + 4 - 8 [promotion for 4 lattes] = 35.3 )
        assertThat(total, is(35.3));
    }

    @Test
    public void testPromotions() {
        CoffeeShop coffeeShop = new CoffeeShop();
        coffeeShop.printMenu();

        coffeeShop.takeOrder(Menu.LATTE, 2);
        coffeeShop.takeOrder(Menu.ESPRESSO, 1);
        coffeeShop.takeOrder(Menu.ESPRESSO, 1);
        coffeeShop.takeOrder(Menu.SANDWICH, 6);
        // Total of order should be $59.0 ( 5.3 + 5.3 + 4 + 4 + 10.10 x6 = 79.2 )
        // Total after discount of 1espressox2lattes should be $75.2 (the cheapest)
        // Total after discount of 5% should be $75.24
        Double total = coffeeShop.printReceipt();
        assertThat(total, is(75.2));

        coffeeShop.takeOrder(Menu.SANDWICH, 4);
        // Total of order should be $119.6 ( 5.3 + 5.3 + 4 + 4 + 10.10 x10 = 119.6 )
        // Total after discount of 1espressox2lattes should be $115.6
        // Total after discount of 5% should be $113.62 (the cheapest)
        total = coffeeShop.printReceipt();
        assertThat(total, is(113.62));
    }

}