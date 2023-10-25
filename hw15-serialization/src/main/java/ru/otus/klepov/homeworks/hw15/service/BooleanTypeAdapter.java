package ru.otus.klepov.homeworks.hw15.service;
import com.google.gson.*;

import java.lang.reflect.Type;

public class BooleanTypeAdapter implements JsonDeserializer<Boolean>  {
    @Override
    public Boolean deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (((JsonPrimitive) json).isBoolean()) {
            return json.getAsBoolean();
        }
        if (((JsonPrimitive) json).isNumber()) {
            var v = json.getAsInt();
            switch (v) {
                case 0:
                    return false;
                case 1:
                    return true;
                default:
                    throw new JsonParseException(String.format("Can't deserialize to boolean int=%d ", v));
            }
        }
        throw new JsonParseException(String.format("Can't deserialize to boolean key=%s ", json));
    }
}
