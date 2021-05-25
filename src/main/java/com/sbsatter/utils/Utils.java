package com.sbsatter.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {
    private static final DateTimeFormatter ISO_8601_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    public static LocalDateTime parseDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, ISO_8601_FORMATTER);
    }

    /**
     * Returns two date time pairs, start and end, where start is 1 hour and end is 4 hours after given param time
     * @param time
     * @return datePair consisting of start and end of transit times
     */
    public static DatePair getRequiredDatePair(LocalDateTime time) {
        return new DatePair(time.plusHours(1), time.plusHours(4));
    }
}
