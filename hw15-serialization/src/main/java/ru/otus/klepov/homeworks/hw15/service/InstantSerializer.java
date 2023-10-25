package ru.otus.klepov.homeworks.hw15.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.time.ZoneId;

public class InstantSerializer implements JsonSerializer<Instant> {
    @Override
    public JsonElement serialize(Instant src, Type typeOfSrc, JsonSerializationContext context) {
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern(InstantTypeAdapter.DATE_PATTERN).withLocale(Locale.US )
                .withZone( ZoneId.of(InstantTypeAdapter.UTC_ZONE));;
        return new JsonPrimitive(formatter.format(src));
    }
}
