package com.inatlas.challenge.utils;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
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

}
