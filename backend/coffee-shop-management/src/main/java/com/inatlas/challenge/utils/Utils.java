package com.inatlas.challenge.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Utils {

    public static Double formatDouble(Double d) {
        BigDecimal bd = new BigDecimal(d).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
