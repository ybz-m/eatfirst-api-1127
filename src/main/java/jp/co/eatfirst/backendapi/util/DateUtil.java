package jp.co.eatfirst.backendapi.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {
    public static final DateTimeFormatter ymdhms = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter ymd = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final DateTimeFormatter ymdhms_nosign = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    public static final DateTimeFormatter chinese_ymdhms = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
    Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date asDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date asUTCDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneOffset.UTC).toInstant());
    }

    public static String asString_ymdhms(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).format(ymdhms);
    }
    public static String asString_chinese_ymdhms(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).format(ymdhms);
    }

    public static String asString_ymd(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).format(ymd);
    }

    public static String asString_ymdhms_nosign(LocalDateTime localDateTime) {
        return localDateTime.atZone(ZoneId.systemDefault()).format(ymdhms_nosign);
    }

    public static LocalDate asLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime asLocalDateTime(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static String asString_ymdhms(Date localDateTime) {
        return asString_ymdhms(asLocalDateTime(localDateTime));
    }

    public static Date asLocalDateTime_ymdhms(String date) {
        return asDate(LocalDateTime.parse(date, ymdhms));
    }
    
    public static Date getNow() {
        return asDate(LocalDateTime.now());
    }
    
    public static String getNow_ymdhms_nosign() {
        return LocalDateTime.now().atZone(ZoneId.systemDefault()).format(ymdhms_nosign);
    }
}
