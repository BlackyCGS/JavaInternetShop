package com.myshop.internetshop.classes.utilities;

import java.security.SecureRandom;
import java.util.Random;

public class NumberParser {

    NumberParser() {
        throw new UnsupportedOperationException(
        "There in no way you can call the constructor of NumberParser");
    }

    public static Integer parseInteger(String value) {
        if (value == null || value.isEmpty()) {
            return 0;
        }

        String numericValue = value.replaceAll("\\D", "");
        if (numericValue.isEmpty()) {
            return 0;
        }

        return Integer.parseInt(numericValue);
    }

    public static Float parseFloatNumber(String value) {
        if (value == null || value.isEmpty()) {
            Random rand = new SecureRandom();
            return rand.nextFloat(2000);
        }

        String numericValue = value.replaceAll("[^0-9.]", "");
        if (numericValue.isEmpty()) {
            Random rand = new SecureRandom();
            return rand.nextFloat(2000);
        }

        return Float.parseFloat(numericValue);
    }
}
