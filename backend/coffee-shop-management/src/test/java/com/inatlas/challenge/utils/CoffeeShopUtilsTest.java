package com.inatlas.challenge.utils;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class CoffeeShopUtilsTest {

    private final static String dateFeb11st = "11-02-2021";

    @Test
    public void testFormatDouble() {

        Double outputDouble = CoffeeShopUtils.formatDouble(new Double(0.0));
        assertThat(outputDouble, is(0.0));

        outputDouble = CoffeeShopUtils.formatDouble(new Double(1.000_002));
        assertThat(outputDouble, is(1.0));

        outputDouble = CoffeeShopUtils.formatDouble(new Double(1.120_002));
        assertThat(outputDouble, is(1.12));

        outputDouble = CoffeeShopUtils.formatDouble(new Double(1_546_564.152));
        assertThat(outputDouble, is(1_546_564.15));

        outputDouble = CoffeeShopUtils.formatDouble(new Double(1_546_564.155));
        assertThat(outputDouble, is(1_546_564.16));

        outputDouble = CoffeeShopUtils.formatDouble(new Double(1_546_564.153_9));
        assertThat(outputDouble, is(1_546_564.15));

    }

    @Test
    public void tesDatesFormat() {

        Date date = null;
        String stringDate = CoffeeShopUtils.formatDate(date);
        assertThat(stringDate, nullValue());
        stringDate = CoffeeShopUtils.formatDatetime(date);
        assertThat(stringDate, nullValue());

        date = new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime();
        stringDate = CoffeeShopUtils.formatDate(date);
        assertThat(stringDate, is("11-02-2014"));
        stringDate = CoffeeShopUtils.formatDatetime(date);
        assertThat(stringDate, is("11-02-2014 00:00:00"));

        date = CoffeeShopUtils.parseDate(null, null);
        assertThat(date, nullValue());
        date = CoffeeShopUtils.parseDate(dateFeb11st, null);
        assertThat(date, nullValue());
        date = CoffeeShopUtils.parseDate(null, CoffeeShopUtils.DATE_FORMAT);
        assertThat(date, nullValue());
        date = CoffeeShopUtils.parseDate("", "");
        assertThat(date, nullValue());
        date = CoffeeShopUtils.parseDate(dateFeb11st, "XXXXX");
        assertThat(date, nullValue());
        date = CoffeeShopUtils.parseDate("11/02/2021", CoffeeShopUtils.DATE_FORMAT);
        assertThat(date, nullValue());
        date = CoffeeShopUtils.parseDate(dateFeb11st, CoffeeShopUtils.TIME_FORMAT);
        assertThat(date, nullValue());

        date = CoffeeShopUtils.parseDate(dateFeb11st, CoffeeShopUtils.DATE_FORMAT);
        assertThat(date, notNullValue());
        stringDate = CoffeeShopUtils.formatDate(date);
        assertThat(stringDate, is(dateFeb11st));
        stringDate = CoffeeShopUtils.formatDatetime(date);
        assertThat(stringDate, is(dateFeb11st + " 00:00:00"));

        date = CoffeeShopUtils.parseDate("11:02:00", CoffeeShopUtils.TIME_FORMAT);
        assertThat(date, notNullValue());
        stringDate = CoffeeShopUtils.formatDate(date);
        assertThat(stringDate, is("01-01-1970"));
        stringDate = CoffeeShopUtils.formatDatetime(date);
        assertThat(stringDate, is("01-01-1970 11:02:00"));

        date = CoffeeShopUtils.parseDate(dateFeb11st + " 12:05:32", CoffeeShopUtils.DATETIME_FORMAT);
        assertThat(date, notNullValue());
        stringDate = CoffeeShopUtils.formatDate(date);
        assertThat(stringDate, is(dateFeb11st));
        stringDate = CoffeeShopUtils.formatDatetime(date);
        assertThat(stringDate, is(dateFeb11st + " 12:05:32"));

    }
}
