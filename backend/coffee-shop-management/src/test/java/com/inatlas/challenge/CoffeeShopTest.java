package com.inatlas.challenge;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CoffeeShopTest {
    
    @Test
    public void testTakeMyFirstOrder(){
        CoffeeShop coffeeShop = new CoffeeShop();
        coffeeShop.printMenu();
        coffeeShop.takeOrder("Latte", 1);
        coffeeShop.takeOrder("Espresso", 1);
        coffeeShop.takeOrder("Sandwich", 1);
        Double total = coffeeShop.printReceipt();
        // Total should be $19.4 ( 5.3 + 4 + 10.10 = 19.4 )
        assertThat(total, is(19.4));
    }

    @Test
    public void testTakeMySecondOrder(){
        CoffeeShop coffeeShop = new CoffeeShop();
        coffeeShop.printMenu();
        coffeeShop.takeOrder("Latte", 2);
        coffeeShop.takeOrder("Espresso", 1);
        coffeeShop.takeOrder("Sandwich", 1);
        Double total = coffeeShop.printReceipt();
        // Total should be $20.7 ( 5.3 + 5.3 + 4 + 10.10 - 4 [promotion] = 20.7 )
        assertThat(total, is(20.7));
    }

    @Test
    public void testTakeMyThirdOrder3(){
        CoffeeShop coffeeShop = new CoffeeShop();
        coffeeShop.printMenu();
        coffeeShop.takeOrder("Latte", 1);
        coffeeShop.takeOrder("Latte", 1);
        coffeeShop.takeOrder("Espresso", 1);
        coffeeShop.takeOrder("Sandwich", 1);
        Double total = coffeeShop.printReceipt();
        // Total should be $20.7 ( 5.3 + 5.3 + 4 + 10.10 - 4 [promotion] = 20.7 )
        assertThat(total, is(20.7));
    }

    @Test
    public void testTakeMyFourthOrder(){
        CoffeeShop coffeeShop = new CoffeeShop();
        coffeeShop.printMenu();
        coffeeShop.takeOrder("Latte", 2);
        coffeeShop.takeOrder("Espresso", 1);
        coffeeShop.takeOrder("Espresso", 1);
        coffeeShop.takeOrder("Sandwich", 1);
        Double total = coffeeShop.printReceipt();
        // Total should be $24.7 ( 5.3 + 5.3 + 4 + 4 + 10.10 - 4 [promotion] = 24.7 )
        assertThat(total, is(24.7));
    }
}
