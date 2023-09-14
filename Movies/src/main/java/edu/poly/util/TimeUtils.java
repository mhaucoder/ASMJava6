package edu.poly.util;

import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class TimeUtils {
    private static final String TIME_FORMAT = "HH:mm";

    public static Time convertLocalTimeToTime(LocalTime localTime) {
        return Time.valueOf(localTime);
    }

    public static LocalTime convertStringToLocalTime(String timeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
        return LocalTime.parse(timeString, formatter);
    }
}
