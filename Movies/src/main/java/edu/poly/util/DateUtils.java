package edu.poly.util;

import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
public class DateUtils {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");


    public static LocalDate parseStringToLocalDate(String dateString) {
        return LocalDate.parse(dateString, formatter);
    }

    public static Date parseStringToDate(String dateString) throws ParseException {
        return dateFormat.parse(dateString);
    }
}
