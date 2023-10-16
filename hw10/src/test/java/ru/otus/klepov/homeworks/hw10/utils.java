package ru.otus.klepov.homeworks.hw10;

import java.util.ArrayList;
import java.util.List;

public class utils {
    @SafeVarargs
    public static <T> List<T> makeList(T... args) {
        List<T> res = new ArrayList<T>();
        for(var item: args) {
            res.add(item);
        }
        return res;
    }
}
