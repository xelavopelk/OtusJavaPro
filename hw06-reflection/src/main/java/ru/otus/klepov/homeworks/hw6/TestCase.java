package ru.otus.klepov.homeworks.hw6;

import java.lang.reflect.InvocationTargetException;

public interface TestCase {
    void run() throws InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException;
    String getName();
}
