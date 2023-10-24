package ru.otus.klepov.homeworks.hw15.service;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.google.protobuf.Timestamp;
import io.vavr.control.Either;
import io.vavr.control.Try;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static ru.otus.klepov.homeworks.hw15.service.InstantTypeAdapter.DATE_PATTERN;

public class Utils {
    public static Either<Throwable, String> readFile(String fileName) {
        return Try.of(() -> Resources.toString(Resources.getResource(fileName), Charsets.UTF_8)).toEither();
    }
    public static Instant parseString2Instant(String date, String zone, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(DATE_PATTERN);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN, Locale.US);
        LocalDateTime localDateTime = LocalDateTime.parse(date, dateTimeFormatter);
        ZoneId zoneId = ZoneId.of(zone);
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
        return zonedDateTime.toInstant();
    }

    public static Timestamp timestampFromInstant(Instant time) {
        return Timestamp
                .newBuilder()
                .setSeconds(time.getEpochSecond())
                .setNanos(time.getNano())
                .build();
    }
}
