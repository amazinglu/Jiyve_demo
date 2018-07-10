package com.example.amazinglu.jiyve_demo.Util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    private static DateFormat completeFormat = new SimpleDateFormat("yyyy MM dd HH:mm", Locale.getDefault());
    private static DateFormat dateFormat = new SimpleDateFormat("EEE", Locale.getDefault());
    private static DateFormat timeFormat = new SimpleDateFormat("HH", Locale.getDefault());

    public static String dateToStringGson(Date date) {
        return date == null ? null : completeFormat.format(date);
    }
    public static Date stringToDateGson(String str) {
        try {
            return str == null ? null : completeFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String dateToString(Date date) {
        return date == null ? null : dateFormat.format(date);
    }

    public static String timeToString(Date date) {
        return date == null ? null : timeFormat.format(date);
    }

    public static boolean hourComparer(Date date1, Date date2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(date1);
        c2.setTime(date2);

        return c1.get(Calendar.HOUR_OF_DAY) <= c2.get(Calendar.HOUR_OF_DAY);
    }
}
