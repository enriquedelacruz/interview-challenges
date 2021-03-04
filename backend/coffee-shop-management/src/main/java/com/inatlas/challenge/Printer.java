package com.inatlas.challenge;

import com.inatlas.challenge.products.Menu;
import com.inatlas.challenge.products.Product;
import com.inatlas.challenge.utils.Utils;

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
    private static final String DAILY_REPORT_LABEL = "DAILY REPORT";
    private static final String PROMOTIONS_LABEL = "PROMOTIONS";
    private static final String PRODUCT_LABEL = "Product";
    private static final String PRICE_LABEL = "Price";
    private static final String TOTAL_LABEL = "Total";
    private static final String PROMOTION_LABEL = "(*) Promotion";
    private static final String CURRENCY = Order.CURRENCY;

    private static Printer printer;

    public Printer() {
    }

    synchronized public static Printer getInstance() {
        if (printer == null) {
            printer = new Printer();
        }
        return printer;
    }

    public void printReceipt(Order order) {
        print(createReceipt(order));
    }

    public void printReceipt(List<Product> orders, double total, String appliedPromotion) {
        print(createReceipt(RECEIPT_LABEL, null, orders, total, appliedPromotion));
    }

    public void printDailyProductsSold(Order dailyProductSold) {
        print(createDailyProductsSold(dailyProductSold));
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


    //Private methods
    private String createDailyProductsSold(Order dailyProductSold) {
        String output = "";
        if (dailyProductSold != null) {
            if (dailyProductSold.getTotal() == 0.0) {
                dailyProductSold.calculateTotalWithoutPromotions();
            }
            //Create a special receipt without promotions
            output = createReceipt(DAILY_REPORT_LABEL, Utils.formatDate(dailyProductSold.getDate()), dailyProductSold.getProducts(), dailyProductSold.getTotal(), null);
        }
        return output;
    }

    private String createReceipt(Order order) {
        String output = "";
        if (order != null) {
            if (order.getTotal() == 0.0) {
                order.calculateTotal();
            }
            output = createReceipt(RECEIPT_LABEL, Utils.formatDate(order.getDate()), order.getProducts(), order.getTotal(), order.getAppliedPromotion());
        }
        return output;
    }

    private String createReceipt(String title, String date,  List<Product> orders, double total, String promotionApplied) {
        //Header
        StringBuilder sbReceipt = new StringBuilder(createHeader(title, date, Arrays.asList(new String[] { PRODUCT_LABEL, PRICE_LABEL})));

        if (orders != null && !orders.isEmpty()) {
            //Body
            sbReceipt.append(orders.stream()
                    .map(p -> {
                        String productNameAnQuantity = p.getQuantity() + " " + p.getName().getName();
                        return productNameAnQuantity
                                + repeatString(".", COLUMN_WIDTH - productNameAnQuantity.length() + 1)
                                + CURRENCY + " " + Utils.formatDouble(p.getPrice()) + ((p.isDiscount())?" (*)":"");
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
        StringBuilder sbMenu = new StringBuilder(createHeader(MENU_LABEL, null, Arrays.asList(new String[] { PRODUCT_LABEL, PRICE_LABEL})));

        //Body
        sbMenu.append(Arrays.stream(Menu.MenuProduct.values())
                .map(p -> {
                    return p.getName()
                            + repeatString(".", COLUMN_WIDTH - p.getName().length() + 1)
                            + CURRENCY + " " + p.getPrice();
                })
                .collect(Collectors.joining("\n")) + "\n");

        //Promotions
        sbMenu.append(repeatString("-", COLUMN_WIDTH * 2) + "\n");
        sbMenu.append(PROMOTIONS_LABEL + "\n");
        sbMenu.append(Menu.availablePromotions.stream()
                .map(p -> {
                    return " - " + p.getName();
                })
                .collect(Collectors.joining("\n")) + "\n");

        //Footer
        sbMenu.append(repeatString("=", COLUMN_WIDTH * 2) + "\n");

        return sbMenu.toString();
    }

    private String createHeader(String title, String date, List<String> columns) {
        StringBuilder sbHeader = new StringBuilder();
        if (columns != null && !columns.isEmpty()) {
            int headerWidth = COLUMN_WIDTH * columns.size();
            sbHeader.append(repeatString("=", headerWidth) + "\n");
            //Title
            sbHeader.append(repeatString(" ", (headerWidth - title.length()) / 2) + title + "\n");
            if (date != null) {
                sbHeader.append(repeatString(" ", (headerWidth - date.length()) / 2) + date + "\n");
            }
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
