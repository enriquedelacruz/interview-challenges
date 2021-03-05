package com.inatlas.challenge.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Static class for application utilities
 */
public final class CoffeeShopUtils {

    /**
     * Config properties for dates format
     */
    public static final String DATE_FORMAT = "dd-MM-yyyy";
    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final String DATETIME_FORMAT = DATE_FORMAT + " " + TIME_FORMAT;
    public static final String LOCALE_TAG = "es-ES";

    //Constructors

    /**
     * Default constructor
     */
    private CoffeeShopUtils() {}

    //Public methods

    /**
     * Method to format a double number with 2 decimal spaces
     * @param doubleNumber input double number
     * @return double number rounded to 2 decimal spaces
     */
    public static Double formatDouble(final Double doubleNumber) {
        final BigDecimal bigdecimalNumber = BigDecimal.valueOf(doubleNumber).setScale(2, RoundingMode.HALF_UP);
        return bigdecimalNumber.doubleValue();
    }

    /**
     * Method to format a Date parameter with the pattern specified in config properties for date
     * @param date Date to be formatted
     * @return string with the date formatted
     */
    public static String formatDate(final Date date) {
        String stringDate = null;
        if (date != null) {
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.forLanguageTag(LOCALE_TAG));
            stringDate = simpleDateFormat.format(date);
        }

        return stringDate;
    }

    /**
     * Method to format a Date parameter with the pattern specified in config properties for date and time
     * @param date Date to be formatted
     * @return string with the date formatted
     */
    public static String formatDatetime(final Date date) {
        String stringDate = null;
        if (date != null) {
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATETIME_FORMAT, Locale.forLanguageTag(LOCALE_TAG));
            stringDate = simpleDateFormat.format(date);
        }

        return stringDate;
    }

    /**
     * Method to parse a Date from a string as parameter with the pattern specified in config properties
     * @param stringDate input string to be parsed into a date
     * @param format pattern of the date. Used for parse the date
     * @return the date parsed
     */
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
