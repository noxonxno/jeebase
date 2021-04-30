package com.jeebase.system.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class LocalDateTimeUtils {

    /**
     * 转年月日
     * @return
     */
    public static String localDateTimeToDateString(LocalDateTime time) {
        if (time==null) {
            return "";
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return df.format(time);
    }

    /**
     * 转年月日 时分秒
     * @return
     */
    public static String localDateTimeToDateTimeString(LocalDateTime time) {
        if (time==null) {
            return "";
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return df.format(time);
    }

    /**
     * LocalDateTime转毫秒时间戳
     *
     * @param localDateTime LocalDateTime
     * @return 时间戳
     */
    public static Long localDateTimeToTimestamp(LocalDateTime localDateTime) {
        try {
            ZoneId zoneId = ZoneId.systemDefault();
            Instant instant = localDateTime.atZone(zoneId).toInstant();
            return instant.toEpochMilli();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 时间戳转LocalDateTime
     *
     * @param timestamp 时间戳
     * @return LocalDateTime
     */
    public static LocalDateTime timestampToLocalDateTime(long timestamp) {
        try {
            Instant instant = Instant.ofEpochMilli(timestamp);
            ZoneId zone = ZoneId.systemDefault();
            return LocalDateTime.ofInstant(instant, zone);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Date转LocalDateTime
     *
     * @param date Date
     * @return LocalDateTime
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        try {
            Instant instant = date.toInstant();
            ZoneId zoneId = ZoneId.systemDefault();
            return instant.atZone(zoneId).toLocalDateTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * LocalDateTime转Date
     *
     * @param localDateTime LocalDateTime
     * @return Date
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        try {
            ZoneId zoneId = ZoneId.systemDefault();
            ZonedDateTime zdt = localDateTime.atZone(zoneId);
            return Date.from(zdt.toInstant());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 计算两个localDateTime之间的毫秒数差
     * @return
     */
    public static Long betweenMilliSecond(LocalDateTime before, LocalDateTime after) {
        return ChronoUnit.MILLIS.between(before, after);
    }

    /**
     * 计算两个localDateTime之间的秒数差
     * @return
     */
    public static Long betweenSecond(LocalDateTime before, LocalDateTime after) {
        return ChronoUnit.SECONDS.between(before, after);
    }

    /**
     * 计算两个localDateTime之间的天数差
     * @return
     */
    public static Long betweenDay(LocalDateTime before, LocalDateTime after) {
        return ChronoUnit.DAYS.between(before, after);
    }

    /**
     * 计算两个localDateTime之间的年数差
     * @return
     */
    public static Long betweenYear(LocalDateTime before, LocalDateTime after) {
        return ChronoUnit.YEARS.between(before, after);
    }

    /**
     * 转年月日 yyyy/mm/dd
     * @return
     */
    public static String getNowDate(LocalDateTime time) {
        if (time==null) {
            return "";
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return df.format(time);
    }

}
