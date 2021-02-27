package com.inatlas.challenge;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PrinterTest {

    @Test
    public void printerBasicTests() {
        Printer.getInstance().print(null);
        //This should print null
        Printer.getInstance().print("");
        //This should print nothing
        Printer.getInstance().print("Hello Coffee Shop");
        //This should print "Hello Coffee Shop"
    }

    @Test
    public void testPrintMenu() {
        Printer.getInstance().printMenu();
        //This should print the whole menu
    }

    @Test
    public void testPrintReceipt() {
        Printer.getInstance().printReceipt(null, 0.0);
        //This should print an empty receipt with total = 0.0

        List<Product> orders = Arrays.asList(new Product[]{
                new Product(Menu.ESPRESSO, 1)
        });
        Printer.getInstance().printReceipt(orders, 5.0);
        //This should print a receipt with 1 espresso 4.0 and total 5.0

        orders = Arrays.asList(new Product[]{
                new Product(Menu.ESPRESSO, 250),
                new Product(Menu.SANDWICH, 10),
                new Product(Menu.LATTE, 15),
                new Product(Menu.ESPRESSO, 100)
        });
        Printer.getInstance().printReceipt(orders, 5.0);
        //This should print a receipt with all products above and total 5.0

    }

}