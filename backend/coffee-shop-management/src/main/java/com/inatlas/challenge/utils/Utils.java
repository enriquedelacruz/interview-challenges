package com.inatlas.challenge.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public static Double formatDouble(Double d) {
        BigDecimal bd = new BigDecimal(d).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static final String DATE_FORMAT = "dd-MM-yyyy";
    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final String DATETIME_FORMAT = DATE_FORMAT + " " + TIME_FORMAT;

    public static String formatDate(Date d) {
        String stringDate = null;
        if (d != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
            stringDate = simpleDateFormat.format(d);
        }

        return stringDate;
    }

    public static String formatDatetime(Date d) {
        String stringDate = null;
        if (d != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATETIME_FORMAT);
            if (simpleDateFormat != null) {
                stringDate = simpleDateFormat.format(d);
            }
        }

        return stringDate;
    }

    public static Date parseDate(String stringDate, String format) {
        Date date = null;
        if (stringDate != null && format != null) {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
                if (simpleDateFormat != null) {
                    date = simpleDateFormat.parse(stringDate);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return date;
    }

}
