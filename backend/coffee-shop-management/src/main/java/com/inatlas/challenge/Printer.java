package com.inatlas.challenge;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Singleton Printer
 */
public class Printer {

    private static final int COLUMN_WIDTH = 15;
    private static final String MENU_LABEL = "COFFEE SHOP MENU";
    private static final String RECEIPT_LABEL = "RECEIPT";
    private static final String QUANTITY_LABEL = "Quantity";
    private static final String PRODUCT_LABEL = "Product";
    private static final String PRICE_LABEL = "Price";
    private static final String TOTAL_LABEL = "Total";
    private static final String PROMOTION_LABEL = "* Promotion";
    private static final String CURRENCY = CoffeeShop.CURRENCY;

    private static Printer printer;

    public Printer() {
    }

    synchronized public static Printer getInstance() {
        if (printer == null) {
            printer = new Printer();
        }
        return printer;
    }

    public void printReceipt(List<Product> orders, double total, String appliedPromotion) {
        print(createReceipt(orders, total, appliedPromotion));
    }

    public void printMenu() {
        print(createMenu());
    }

    /**
     * Prints parameter string through standard output.
     * The output can be change easily (file, email, etc) and transforms this printer in another
     * @param output
     */
    public void print(String output) {
        //Prints through standard output
        System.out.println(output);
    }


    private String createReceipt(List<Product> orders, double total, String promotionApplied) {
        //Header
        StringBuilder sbReceipt = new StringBuilder(createHeader(RECEIPT_LABEL, Arrays.asList(new String[] { PRODUCT_LABEL, PRICE_LABEL})));

        if (orders != null && !orders.isEmpty()) {
            //Body
            sbReceipt.append(orders.stream()
                    .map(p -> {
                        String productNameAnQuantity = p.getQuantity() + " " + p.getName().getName();
                        return productNameAnQuantity
                                + repeatString(".", COLUMN_WIDTH - productNameAnQuantity.length() + 1)
                                + CURRENCY + " " + p.getPrice();
                    })
                    .collect(Collectors.joining("\n")) + "\n");
        }

        //Total
        sbReceipt.append(repeatString("-", COLUMN_WIDTH * 2) + "\n");
        sbReceipt.append(TOTAL_LABEL + repeatString(".", COLUMN_WIDTH - TOTAL_LABEL.length() + 1) + CURRENCY + " " + total + "\n");

        //Promotion
        if (promotionApplied != null && !promotionApplied.isEmpty()) {
            sbReceipt.append(PROMOTION_LABEL + ": " + promotionApplied + "\n");
        }

        //Footer
        sbReceipt.append(repeatString("=", COLUMN_WIDTH * 2) + "\n");

        return sbReceipt.toString();
    }

    private String createMenu() {
        //Header
        StringBuilder sbMenu = new StringBuilder(createHeader(MENU_LABEL, Arrays.asList(new String[] { PRODUCT_LABEL, PRICE_LABEL})));

        //Body
        sbMenu.append(Arrays.stream(Menu.values())
                .map(p -> {
                    return p.getName()
                            + repeatString(".", COLUMN_WIDTH - p.getName().length() + 1)
                            + CURRENCY + " " + p.getPrice();
                })
                .collect(Collectors.joining("\n")) + "\n");

        //Footer
        sbMenu.append(repeatString("=", COLUMN_WIDTH * 2) + "\n");

        return sbMenu.toString();
    }

    private String createHeader(String title, List<String> columns) {
        StringBuilder sbHeader = new StringBuilder();
        if (columns != null && !columns.isEmpty()) {
            int headerWidth = COLUMN_WIDTH * columns.size();
            sbHeader.append(repeatString("=", headerWidth) + "\n");
            //Title
            sbHeader.append(repeatString(" ", (headerWidth - title.length()) / 2) + title + "\n");
            sbHeader.append(repeatString("-", headerWidth) + "\n");

            //Columns
            for (String column : columns) {
                sbHeader.append(column + repeatString(" ", COLUMN_WIDTH - column.length() + 1));
            }
            sbHeader.append("\n");

            sbHeader.append(repeatString("-", headerWidth) + "\n");
        }

        return sbHeader.toString();
    }

    private String repeatString(String stringToRepeat, int n) {
        if (stringToRepeat != null) {
            return new String(new char[n]).replace("\0", stringToRepeat);
        }
        return null;
    }
}
