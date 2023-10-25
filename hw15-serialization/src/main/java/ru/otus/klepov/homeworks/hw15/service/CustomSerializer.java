package ru.otus.klepov.homeworks.hw15.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import io.vavr.control.Either;
import io.vavr.control.Try;

import java.io.Writer;
import java.lang.reflect.ParameterizedType;
import java.time.Instant;


public class CustomSerializer<T> {
    @SuppressWarnings("unchecked")
    public Either<Throwable, T> deserialize(String json) {
        final ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
        var tp = superclass.getActualTypeArguments()[0];
        GsonBuilder gsonBldr = new GsonBuilder();
        gsonBldr.registerTypeAdapter(Boolean.class, new BooleanTypeAdapter());
        gsonBldr.registerTypeAdapter(Instant.class, new InstantTypeAdapter());
        return Try
                .of(() -> (T) (gsonBldr.create().fromJson(json, tp)))
                .toEither();
    }

    @SuppressWarnings("unchecked")
    public Either<Throwable, Boolean> serialize(Writer writer, T obj) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Instant.class, new InstantSerializer())
                .create();
        return Try
                .of(() -> {
                    gson.toJson(obj, writer);
                    writer.flush();
                    return true;
                }).toEither();
    }
}
