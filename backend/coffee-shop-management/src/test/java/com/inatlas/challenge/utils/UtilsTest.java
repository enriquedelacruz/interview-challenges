package com.inatlas.challenge.utils;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class UtilsTest {

    @Test
    public void testFormatDouble() {

        Double outputDouble = Utils.formatDouble(new Double(0.0));
        assertThat(outputDouble, is(0.0));

        outputDouble = Utils.formatDouble(new Double(1.000002));
        assertThat(outputDouble, is(1.0));

        outputDouble = Utils.formatDouble(new Double(1.120002));
        assertThat(outputDouble, is(1.12));

        outputDouble = Utils.formatDouble(new Double(1546564.152));
        assertThat(outputDouble, is(1546564.15));

        outputDouble = Utils.formatDouble(new Double(1546564.155));
        assertThat(outputDouble, is(1546564.16));

        outputDouble = Utils.formatDouble(new Double(1546564.1539));
        assertThat(outputDouble, is(1546564.15));

    }

    @Test
    public void tesDatesFormat() {

        Date date = null;
        String stringDate = Utils.formatDate(date);
        assertThat(stringDate, nullValue());
        stringDate = Utils.formatDatetime(date);
        assertThat(stringDate, nullValue());

        date = new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime();
        stringDate = Utils.formatDate(date);
        assertThat(stringDate, is("11-02-2014"));
        stringDate = Utils.formatDatetime(date);
        assertThat(stringDate, is("11-02-2014 00:00:00"));

        date = Utils.parseDate(null, null);
        assertThat(date, nullValue());
        date = Utils.parseDate("11-02-2021", null);
        assertThat(date, nullValue());
        date = Utils.parseDate(null, Utils.DATE_FORMAT);
        assertThat(date, nullValue());
        date = Utils.parseDate("", "");
        assertThat(date, nullValue());
        date = Utils.parseDate("11-02-2021", "XXXXX");
        assertThat(date, nullValue());
        date = Utils.parseDate("11/02/2021", Utils.DATE_FORMAT);
        assertThat(date, nullValue());
        date = Utils.parseDate("11-02-2021", Utils.TIME_FORMAT);
        assertThat(date, nullValue());

        date = Utils.parseDate("11-02-2021", Utils.DATE_FORMAT);
        assertThat(date, notNullValue());
        stringDate = Utils.formatDate(date);
        assertThat(stringDate, is("11-02-2021"));
        stringDate = Utils.formatDatetime(date);
        assertThat(stringDate, is("11-02-2021 00:00:00"));

        date = Utils.parseDate("11:02:00", Utils.TIME_FORMAT);
        assertThat(date, notNullValue());
        stringDate = Utils.formatDate(date);
        assertThat(stringDate, is("01-01-1970"));
        stringDate = Utils.formatDatetime(date);
        assertThat(stringDate, is("01-01-1970 11:02:00"));

        date = Utils.parseDate("11-02-2021 12:05:32", Utils.DATETIME_FORMAT);
        assertThat(date, notNullValue());
        stringDate = Utils.formatDate(date);
        assertThat(stringDate, is("11-02-2021"));
        stringDate = Utils.formatDatetime(date);
        assertThat(stringDate, is("11-02-2021 12:05:32"));

    }
}
