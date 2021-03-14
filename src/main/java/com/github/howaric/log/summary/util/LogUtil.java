package com.github.howaric.log.summary.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class LogUtil {

    private static final int LINE_TOTAL_LENGTH = 100;
    private static final String EQUAL = "=";
    private static final String STAR = "*";
    private static final String BLANK = " ";
    private static final String NEXT_ROW = "\n";
    private static final String COLON = ":";
    private static final String ZERO = "0";
    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String alignCenter(String title) {
        return alignCenter(title, EQUAL);
    }

    public static String alignCenter(String title, String mark) {
        int length = title.length();
        int margin = (LINE_TOTAL_LENGTH - length) / 2;
        StringBuilder line = new StringBuilder();
        for (int i = 1; i < margin; i++) {
            line.append(mark);
        }
        line.append(BLANK);
        line.append(title);
        line.append(BLANK);
        for (int i = 1; i < margin; i++) {
            line.append(mark);
        }
        if (length % 2 != 0) {
            line.append(mark);
        }
        return line.toString();
    }

    public static String fillMapInBox(Map<String, String> fillMap) {
        StringBuilder result = new StringBuilder();
        int maxLeft = 0;
        int maxRight = 0;
        for (Map.Entry<String, String> each : fillMap.entrySet()) {
            int leftWidth = each.getKey().length();
            int rightWidth = each.getValue().length();
            if (leftWidth > maxLeft) {
                maxLeft = leftWidth;
            }
            if (rightWidth > maxRight) {
                maxRight = rightWidth;
            }
        }
        int boxWidth = maxLeft + maxRight + 8;
        for (int i = 0; i < boxWidth; i++) {
            result.append(STAR);
        }
        result.append(NEXT_ROW);
        for (Map.Entry<String, String> each : fillMap.entrySet()) {
            String key = each.getKey();
            String value = each.getValue();
            result.append(STAR);
            result.append(BLANK);
            result.append(BLANK);
            result.append(key);
            result.append(COLON);
            int patch = boxWidth - 7 - key.length() - value.length();
            for (int k = 0; k < patch; k++) {
                result.append(BLANK);
            }
            result.append(value);
            result.append(BLANK);
            result.append(BLANK);
            result.append(STAR);
            result.append(NEXT_ROW);
        }
        for (int i = 0; i < boxWidth; i++) {
            result.append(STAR);
        }
        return result.toString();
    }

    public static String formatDate(Long time) {
        if (time == null) {
            return StringUtils.EMPTY;
        }
        Date date = new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
        return simpleDateFormat.format(date);
    }

    public static String secondToTime(int second) {
        int h = second / 3600;
        int m = second % 3600 / 60;
        int s = second % 60;
        StringBuilder result = new StringBuilder();
        if (h > 0) {
            result.append(h > 9 ? h : ZERO + h);
            result.append(COLON);
        }
        if (m == 0) {
            result.append(ZERO + ZERO);
        } else if (m > 0) {
            result.append(m > 9 ? m : ZERO + m);
        }
        result.append(COLON);
        result.append(s > 9 ? s : ZERO + s);
        return result.toString();
    }

}
