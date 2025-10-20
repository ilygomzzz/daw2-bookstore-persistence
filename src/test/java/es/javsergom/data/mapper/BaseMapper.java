package es.javsergom.data.mapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BaseMapper {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    protected static LocalDateTime parseDate(String date) {
        return LocalDateTime.parse(date, DATE_FORMATTER);
    }

    protected static Integer parseInt(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        } else {
            return Integer.parseInt(value);
        }
    }

    protected static BigDecimal parseBigDecimal(String value) {
        if(value==null || value.trim().isEmpty()){
            return null;
        } else {
            return new BigDecimal(value);
        }
    }

    protected static Boolean parseBoolean(String value) {
        if(value==null || value.trim().isEmpty()){
            return null;
        } else {
            return Boolean.parseBoolean(value);
        }
    }

    protected static Double parseDouble(String value) {
        if(value==null || value.trim().isEmpty()){
            return null;
        } else {
            return Double.parseDouble(value);
        }
    }

    protected static String parseString(String value) {
        if(value==null || value.trim().isEmpty()){
            return null;
        } else {
            return value.trim();
        }
    }

    protected static Long parseLong(String value) {
        if(value==null || value.trim().isEmpty()){
            return null;
        } else {
            return Long.parseLong(value);
        }
    }

}
