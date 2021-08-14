package com.urise.webapp.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/yyyy");

    public static LocalDate of(int ears, Month month) {
        YearMonth d = YearMonth.of(ears, month);
        return LocalDate.of(d.getYear(), d.getMonth(), 1);
    }

    public static LocalDate parser(String date) {
        if (date.trim().length() == 0 || date.equals("to this day")) {
            return NOW;
        }
        YearMonth yearMonth = YearMonth.parse(date, DATE_FORMATTER);
        return LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
    }

    public static String format(LocalDate localDate) {
        if (localDate == null) {
            return "";
        }
        return localDate.equals(NOW) ? "to this day" : localDate.format(DATE_FORMATTER);
    }
}