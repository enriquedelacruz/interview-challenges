package com.inatlas.challenge.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class CoffeeShopUtils {

    public static final String DATE_FORMAT = "dd-MM-yyyy";
    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final String DATETIME_FORMAT = DATE_FORMAT + " " + TIME_FORMAT;
    public static final String LOCALE_TAG = "es-ES";

    //Constructors
    private CoffeeShopUtils() {}

    //Public methods
    public static Double formatDouble(final Double doubleNumber) {
        final BigDecimal bigdecimalNumber = BigDecimal.valueOf(doubleNumber).setScale(2, RoundingMode.HALF_UP);
        return bigdecimalNumber.doubleValue();
    }

    public static String formatDate(final Date date) {
        String stringDate = null;
        if (date != null) {
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.forLanguageTag(LOCALE_TAG));
            stringDate = simpleDateFormat.format(date);
        }

        return stringDate;
    }

    public static String formatDatetime(final Date date) {
        String stringDate = null;
        if (date != null) {
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATETIME_FORMAT, Locale.forLanguageTag(LOCALE_TAG));
            stringDate = simpleDateFormat.format(date);
        }

        return stringDate;
    }

    public static Date parseDate(final String stringDate, final String format) {
        Date date = null;
        if (stringDate != null && format != null) {
            try {
                final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.forLanguageTag(LOCALE_TAG));
                date = simpleDateFormat.parse(stringDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return date;
    }

}
