package br.com.mddeveloper.Util;

import java.sql.Date;
import java.time.LocalDate;

public class DateUtils {
    public static Date toSqlDate(LocalDate localDate) {
        return (localDate != null) ? Date.valueOf(localDate) : null;
    }

    public static LocalDate toLocalDate(Date sqlDate) {
        return (sqlDate != null) ? sqlDate.toLocalDate() : null;
    }

    public static String toLocalDateString(Date date) {
        if (date != null) {
            return date.toLocalDate().toString();
        }
        return null;
    }

}
