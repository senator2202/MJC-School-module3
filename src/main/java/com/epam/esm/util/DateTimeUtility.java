package com.epam.esm.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeUtility {
    private static final String TIME_ZONE = "UTC";
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm'Z'";

    public static String getCurrentDateIso() {
        TimeZone tz = TimeZone.getTimeZone(TIME_ZONE);
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        df.setTimeZone(tz);
        return df.format(new Date());
    }
}
