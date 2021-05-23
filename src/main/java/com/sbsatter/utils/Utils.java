package com.sbsatter.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {
    private static final DateTimeFormatter ISO_8601_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    public static LocalDateTime parseDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, ISO_8601_FORMATTER);
    }
}
