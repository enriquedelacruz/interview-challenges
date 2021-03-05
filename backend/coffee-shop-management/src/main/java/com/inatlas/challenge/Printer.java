package com.inatlas.challenge;

import com.inatlas.challenge.products.CoffeeShopMenu;
import com.inatlas.challenge.products.Product;
import com.inatlas.challenge.utils.CoffeeShopUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Singleton Printer class to print receipts, reports and menu
 */
public class Printer {

    /**
     * Config properties for the Printer
     */
    private static final int COLUMN_WIDTH = 15;
    private static final String MENU_LABEL = "COFFEE SHOP MENU";
    private static final String RECEIPT_LABEL = "RECEIPT";
    private static final String DAILY_REPORT_LABEL = "DAILY REPORT";
    private static final String PROMOTIONS_LABEL = "PROMOTIONS";
    private static final String PRODUCT_LABEL = "Product";
    private static final String PRICE_LABEL = "Price";
    private static final String TOTAL_LABEL = "Total";
    private static final String PROMOTION_LABEL = "(*) Promotion";

    /**
     * Instance property for singleton class
     */
    private static Printer printerInstance;

    /**
     * Method to get the singleton instance for Printer
     * @return
     */
    public static synchronized  Printer getInstance() {
        if (printerInstance == null) {
            printerInstance = new Printer();
        }
        return printerInstance;
    }

    /**
     * Method to print the receipt of an order
     * @param products product list of the order to be printed
     * @param total total price of the order
     * @param appliedPromotion name of the applied promotion
     */
    public void printReceipt(final List<Product> products, final double total, final String appliedPromotion) {
        print(createReceipt(RECEIPT_LABEL, null, products, total, appliedPromotion));
    }

    /**
     * Method to print the daily products sold report
     * @param dailyProductSold 'virtual' order that stores the report data to be printed
     */
    public void printDailyProductsSold(final Order dailyProductSold) {
        print(createDailyProductsSold(dailyProductSold));
    }

    /**
     * Method to print the whole menu
     */
    public void printMenu() {
        print(createMenu());
    }

    /**
     * Prints parameter string through standard output.
     * The output can be change easily (file, email, etc) and transforms this printer in another
     * @param output
     */
    public void print(final String output) {
        //Prints through standard output
        System.out.println(output);
    }


    //Private methods

    /**
     * Method to create the text to be printed for the daily products sold report
     * @param dailyProductSold 'virtual' order that storages the report information
     * @return string text of the report to be printed
     */
    private String createDailyProductsSold(final Order dailyProductSold) {
        String output = "";
        if (dailyProductSold != null) {
            if (dailyProductSold.getTotal() == 0.0) {
                dailyProductSold.calculateTotalWithoutPromotions();
            }
            //Create a special receipt without promotions
            output = createReceipt(DAILY_REPORT_LABEL, CoffeeShopUtils.formatDate(dailyProductSold.getDate()), dailyProductSold.getProducts(), dailyProductSold.getTotal(), null);
        }
        return output;
    }

    /**
     * Method to create the text to be printed for the order receipt
     * @param title title of the receipt
     * @param date date of the order
     * @param products product list of the order
     * @param total total price of the order
     * @param promotionApplied name of the applied promotion
     * @return string text of the receipt to be printed
     */
    private String createReceipt(final String title, final String date, final List<Product> products, final double total, final String promotionApplied) {
        //Header
        final StringBuilder sbReceipt = new StringBuilder(createHeader(title, date, Arrays.asList(PRODUCT_LABEL, PRICE_LABEL)));

        if (products != null && !products.isEmpty()) {
            //Body
            sbReceipt.append(products.stream()
                    .map(p -> {
                        final String productNameAnQuantity = p.getQuantity() + " " + p.getMenuProduct().getName();
                        return productNameAnQuantity
                                + repeatString(".", COLUMN_WIDTH - productNameAnQuantity.length() + 1)
                                + CoffeeShop.CURRENCY + " " + CoffeeShopUtils.formatDouble(p.getPrice()) + ((p.isDiscount())?" (*)":"");
                    })
                    .collect(Collectors.joining("\n")));
            sbReceipt.append("\n");
        }

        //Total
        sbReceipt.append(repeatString("-", COLUMN_WIDTH * 2));
        sbReceipt.append("\n");
        sbReceipt.append(TOTAL_LABEL);
        sbReceipt.append(repeatString(".", COLUMN_WIDTH - TOTAL_LABEL.length() + 1));
        sbReceipt.append(CoffeeShop.CURRENCY);
        sbReceipt.append(" ");
        sbReceipt.append(total);
        sbReceipt.append("\n");

        //Promotion
        if (promotionApplied != null && !promotionApplied.isEmpty()) {
            sbReceipt.append(PROMOTION_LABEL);
            sbReceipt.append(": ");
            sbReceipt.append(promotionApplied);
            sbReceipt.append("\n");
        }

        //Footer
        sbReceipt.append(repeatString("=", COLUMN_WIDTH * 2));
        sbReceipt.append("\n");

        return sbReceipt.toString();
    }

    /**
     * Method to create the text to be printed for the menu. This prints all the products with prices
     * and the available promotions
     * @return string text of the menu to be printed
     */
    private String createMenu() {
        //Header
        final StringBuilder sbMenu = new StringBuilder(createHeader(MENU_LABEL, null, Arrays.asList(PRODUCT_LABEL, PRICE_LABEL)));

        //Body
        sbMenu.append(Arrays.stream(CoffeeShopMenu.MenuProduct.values())
                .map(p -> p.getName()
                            + repeatString(".", COLUMN_WIDTH - p.getName().length() + 1)
                            + CoffeeShop.CURRENCY + " " + p.getPrice())
                .collect(Collectors.joining("\n")));
        sbMenu.append("\n");

        //Promotions
        sbMenu.append(repeatString("-", COLUMN_WIDTH * 2));
        sbMenu.append("\n");
        sbMenu.append(PROMOTIONS_LABEL);
        sbMenu.append("\n");
        sbMenu.append(CoffeeShopMenu.getAvailablePromotions().stream()
                .map(p -> " - " + p.getName())
                .collect(Collectors.joining("\n")));
        sbMenu.append("\n");

        //Footer
        sbMenu.append(repeatString("=", COLUMN_WIDTH * 2));
        sbMenu.append("\n");

        return sbMenu.toString();
    }

    /**
     * Method to create the text to be printed of the receipt, report or menu header
     * @param title header title
     * @param date date to be printed
     * @param columns columns of the receipt, report or menu
     * @return string text of the header to be printed
     */
    private String createHeader(final String title, final String date, final List<String> columns) {
        final StringBuilder sbHeader = new StringBuilder();
        if (columns != null && !columns.isEmpty()) {
            final int headerWidth = COLUMN_WIDTH * columns.size();
            sbHeader.append(repeatString("=", headerWidth));
            sbHeader.append("\n");
            //Title
            sbHeader.append(repeatString(" ", (headerWidth - title.length()) / 2));
            sbHeader.append(title);
            sbHeader.append("\n");
            if (date != null) {
                sbHeader.append(repeatString(" ", (headerWidth - date.length()) / 2));
                sbHeader.append(date);
                sbHeader.append("\n");
            }
            sbHeader.append(repeatString("-", headerWidth));
            sbHeader.append("\n");

            //Columns
            for (final String column : columns) {
                sbHeader.append(column);
                sbHeader.append(repeatString(" ", COLUMN_WIDTH - column.length() + 1));
            }
            sbHeader.append("\n");

            sbHeader.append(repeatString("-", headerWidth));
            sbHeader.append("\n");
        }

        return sbHeader.toString();
    }

    /**
     * Method to create blank spaces to be printed. It is used to print separate lines or center texts
     * @param stringToRepeat string to be repeated
     * @param repetitions repetitions number of the string param to be returned
     * @return a string joining the param string repeated
     */
    private String repeatString(final String stringToRepeat, final int repetitions) {
        String outputString = null;
        if (stringToRepeat != null) {
            outputString = new String(new char[repetitions]).replace("\0", stringToRepeat);
        }
        return outputString;
    }
}
