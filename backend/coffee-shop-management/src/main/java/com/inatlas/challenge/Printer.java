package com.inatlas.challenge;

import com.inatlas.challenge.products.CoffeeShopMenu;
import com.inatlas.challenge.products.Product;
import com.inatlas.challenge.utils.CoffeeShopUtils;

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

    private static Printer printerInstance;

    public static synchronized  Printer getInstance() {
        if (printerInstance == null) {
            printerInstance = new Printer();
        }
        return printerInstance;
    }

    public void printReceipt(final Order order) {
        print(createReceipt(order));
    }

    public void printReceipt(final List<Product> orders, final double total, final String appliedPromotion) {
        print(createReceipt(RECEIPT_LABEL, null, orders, total, appliedPromotion));
    }

    public void printDailyProductsSold(final Order dailyProductSold) {
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
    public void print(final String output) {
        //Prints through standard output
        System.out.println(output);
    }


    //Private methods
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

    private String createReceipt(final Order order) {
        String output = "";
        if (order != null) {
            if (order.getTotal() == 0.0) {
                order.calculateTotal();
            }
            output = createReceipt(RECEIPT_LABEL, CoffeeShopUtils.formatDate(order.getDate()), order.getProducts(), order.getTotal(), order.getAppliedPromotion());
        }
        return output;
    }

    private String createReceipt(final String title, final String date, final List<Product> orders, final double total, final String promotionApplied) {
        //Header
        final StringBuilder sbReceipt = new StringBuilder(createHeader(title, date, Arrays.asList(PRODUCT_LABEL, PRICE_LABEL)));

        if (orders != null && !orders.isEmpty()) {
            //Body
            sbReceipt.append(orders.stream()
                    .map(p -> {
                        final String productNameAnQuantity = p.getQuantity() + " " + p.getName().getName();
                        return productNameAnQuantity
                                + repeatString(".", COLUMN_WIDTH - productNameAnQuantity.length() + 1)
                                + CURRENCY + " " + CoffeeShopUtils.formatDouble(p.getPrice()) + ((p.isDiscount())?" (*)":"");
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
        final StringBuilder sbMenu = new StringBuilder(createHeader(MENU_LABEL, null, Arrays.asList(PRODUCT_LABEL, PRICE_LABEL)));

        //Body
        sbMenu.append(Arrays.stream(CoffeeShopMenu.MenuProduct.values())
                .map(p -> p.getName()
                            + repeatString(".", COLUMN_WIDTH - p.getName().length() + 1)
                            + CURRENCY + " " + p.getPrice())
                .collect(Collectors.joining("\n")) + "\n");

        //Promotions
        sbMenu.append(repeatString("-", COLUMN_WIDTH * 2) + "\n");
        sbMenu.append(PROMOTIONS_LABEL + "\n");
        sbMenu.append(CoffeeShopMenu.getAvailablePromotions().stream()
                .map(p -> " - " + p.getName())
                .collect(Collectors.joining("\n")) + "\n");

        //Footer
        sbMenu.append(repeatString("=", COLUMN_WIDTH * 2) + "\n");

        return sbMenu.toString();
    }

    private String createHeader(final String title, final String date, final List<String> columns) {
        final StringBuilder sbHeader = new StringBuilder();
        if (columns != null && !columns.isEmpty()) {
            final int headerWidth = COLUMN_WIDTH * columns.size();
            sbHeader.append(repeatString("=", headerWidth) + "\n");
            //Title
            sbHeader.append(repeatString(" ", (headerWidth - title.length()) / 2) + title + "\n");
            if (date != null) {
                sbHeader.append(repeatString(" ", (headerWidth - date.length()) / 2) + date + "\n");
            }
            sbHeader.append(repeatString("-", headerWidth) + "\n");

            //Columns
            for (final String column : columns) {
                sbHeader.append(column + repeatString(" ", COLUMN_WIDTH - column.length() + 1));
            }
            sbHeader.append("\n");

            sbHeader.append(repeatString("-", headerWidth) + "\n");
        }

        return sbHeader.toString();
    }

    private String repeatString(final String stringToRepeat, final int repetitions) {
        String outputString = null;
        if (stringToRepeat != null) {
            outputString = new String(new char[repetitions]).replace("\0", stringToRepeat);
        }
        return outputString;
    }
}
