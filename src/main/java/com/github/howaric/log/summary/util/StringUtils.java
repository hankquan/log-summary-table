package com.github.howaric.log.summary.util;

public class StringUtils {

    public static final String EMPTY = "";

    public static Boolean isEmpty(String value) {
        if (value == null || "".equals(value)) {
            return true;
        }
        return false;
    }

}
