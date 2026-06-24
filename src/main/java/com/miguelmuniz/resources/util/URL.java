package com.miguelmuniz.resources.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class URL {

    public static String decodeParam(String text) {
        return URLDecoder.decode(text, StandardCharsets.UTF_8);
    }

    private static DateTimeFormatter fmt =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static Instant convertDate(String textDate, Instant defaultValue) {
        try {
            LocalDate date = LocalDate.parse(textDate, fmt);
            return date.atStartOfDay(ZoneId.systemDefault()).toInstant();
        }
        catch (DateTimeParseException e) {
            return defaultValue;
        }
    }
}
