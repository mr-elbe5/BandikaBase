package de.elbe5.base;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateHelper {

    public static Date asDate(LocalDate localDate) {
        if (localDate==null)
            return null;
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date asDate(LocalDateTime localDateTime) {
        if (localDateTime==null)
            return null;
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate asLocalDate(Date date) {
        if (date==null)
            return null;
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime asLocalDateTime(Date date) {
        if (date==null)
            return null;
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static long asMillis(LocalDate localDate) {
        if (localDate==null)
            return 0;
        return asDate(localDate).getTime();
    }

    public static long asMillis(LocalDateTime localDateTime) {
        if (localDateTime==null)
            return 0;
        return asDate(localDateTime).getTime();
    }

    public static LocalDate asLocalDate(long millis) {
        if (millis==0)
            return null;
        return Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime asLocalDateTime(long millis) {
        if (millis==0)
            return null;
        return Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static String getDatePattern(){
        return LocalizedStrings.string("system.datePattern");
    }

    public static String getDateTimePattern(){
        return LocalizedStrings.string("system.dateTimePattern");
    }

    public static String getTimePattern(){
        return LocalizedStrings.string("system.timePattern");
    }

    public static String toHtmlDate(LocalDate date) {
        if (date == null)
            return "";
        return date.format(DateTimeFormatter.ofPattern(getDatePattern()));
    }

    public static String toHtmlDate(LocalDateTime date) {
        if (date == null)
            return "";
        return date.format(DateTimeFormatter.ofPattern(getDatePattern()));
    }

    public static LocalDate fromDate(String s) {
        if (s == null || s.isEmpty())
            return null;
        return LocalDate.parse(s, DateTimeFormatter.ofPattern(getDatePattern()));
    }

    public static String toHtmlTime(LocalTime date) {
        if (date == null)
            return "";
        return date.format(DateTimeFormatter.ofPattern(getTimePattern()));
    }

    public static LocalTime fromTime(String s) {
        if (s == null || s.isEmpty())
            return null;
        return LocalTime.parse(s, DateTimeFormatter.ofPattern(getTimePattern()));
    }

    public static String toHtmlDateTime(LocalDateTime date) {
        if (date == null)
            return "";
        return date.format(DateTimeFormatter.ofPattern(getDateTimePattern()));
    }

    public static LocalDateTime fromDateTime(String s) {
        if (s == null || s.isEmpty())
            return null;
        return LocalDateTime.parse(s, DateTimeFormatter.ofPattern(getDateTimePattern()));
    }
}
