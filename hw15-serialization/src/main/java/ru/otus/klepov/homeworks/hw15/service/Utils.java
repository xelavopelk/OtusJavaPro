package ru.otus.klepov.homeworks.hw15.service;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import io.vavr.control.Either;

import java.io.IOException;

public class Utils {
    public static Either<Throwable, String> readFile(String fileName) {
        try {
            return Either.right(Resources.toString(Resources.getResource(fileName), Charsets.UTF_8));
        }
        catch(Exception ex) {
            return Either.left(ex);
        }
    }
    }
