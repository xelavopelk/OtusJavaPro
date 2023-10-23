package ru.otus.klepov.homeworks.hw15.service;

import com.google.gson.*;
import ru.otus.klepov.homeworks.hw15.domain.olddto.Message;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTypeAdapter implements JsonDeserializer<Date> {
    public static String DATE_PATTERN = "MM-dd-yyyy HH:mm:ss";
    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (((JsonPrimitive) json).isNumber()) {
            var v = json.getAsLong();
            return new Date(v);
        }
        if (((JsonPrimitive) json).isString()) {
            var v = json.getAsString();
            SimpleDateFormat df = new SimpleDateFormat(DATE_PATTERN);
            try {
                return df.parse(v);
            } catch (ParseException e) {
                throw new JsonParseException(e);
            }
        }
        throw new JsonParseException(String.format("Can't deserialize to Date key=%s ", json));
    }
}
