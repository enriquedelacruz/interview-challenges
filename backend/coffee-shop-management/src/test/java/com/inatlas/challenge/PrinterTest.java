package com.inatlas.challenge;

import com.inatlas.challenge.products.CoffeeShopMenu;
import com.inatlas.challenge.products.Product;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class PrinterTest {

    @Test
    public void printerBasicTests() {

        assertThat(Printer.getInstance(), notNullValue());
        //Test singleton Printer
        assertThat(Printer.getInstance().toString(), is(Printer.getInstance().toString()));

        Printer.getInstance().print(null);
        //This should print null
        Printer.getInstance().print("");
        //This should print nothing
        Printer.getInstance().print("Hello Coffee Shop");
        //This should print "Hello Coffee Shop"

    }

    @Test
    public void testPrintMenu() {
        assertThat(Printer.getInstance(), notNullValue());
        assertThat(CoffeeShopMenu.MenuProduct.values(), notNullValue());

        Printer.getInstance().printMenu();
        //This should print the whole menu
    }

    @Test
    public void testPrintReceipt() {

        assertThat(Printer.getInstance(), notNullValue());

        Printer.getInstance().printReceipt(null, 0.0, "");
        //This should print an empty receipt with total = 0.0

        List<Product> orders = Arrays.asList(new Product[]{
                new Product(CoffeeShopMenu.MenuProduct.ESPRESSO, 1)
        });
        Printer.getInstance().printReceipt(orders, 5.0, "promo");
        //This should print a receipt with 1 espresso 4.0 and total 5.0

        orders = Arrays.asList(new Product[]{
                new Product(CoffeeShopMenu.MenuProduct.ESPRESSO, 250),
                new Product(CoffeeShopMenu.MenuProduct.SANDWICH, 10),
                new Product(CoffeeShopMenu.MenuProduct.LATTE, 15),
                new Product(CoffeeShopMenu.MenuProduct.ESPRESSO, 100)
        });
        Printer.getInstance().printReceipt(orders, 5.0, "promo");
        //This should print a receipt with all products above and total 5.0

    }

}