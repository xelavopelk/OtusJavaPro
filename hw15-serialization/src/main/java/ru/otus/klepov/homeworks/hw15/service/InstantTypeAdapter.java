package ru.otus.klepov.homeworks.hw15.service;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


public class InstantTypeAdapter implements JsonDeserializer<Instant> {
    public static String DATE_PATTERN = "MM-dd-yyyy HH:mm:ss";
    public static String UTC_ZONE = "UTC";

    @Override
    public Instant deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (((JsonPrimitive) json).isNumber()) {
            var v = json.getAsLong();
            return Instant.ofEpochMilli(v);
        }
        if (((JsonPrimitive) json).isString()) {
            var v = json.getAsString();
            return Utils.parseString2Instant(v,UTC_ZONE,DATE_PATTERN);
        }
        throw new JsonParseException(String.format("Can't deserialize to Date key=%s ", json));
    }
}
