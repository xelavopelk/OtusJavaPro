package ru.otus.klepov.homeworks.hw6;

import java.util.Arrays;



public class Program {
    public static void main(String[] args) throws NoSuchMethodException {
        Arrays.asList("SimpleJUnitTest", "NoTestSimpleJUnitTest")
                .stream()
                .map(tc -> new TestCaseFactoryImpl(tc))
                .map(tf -> new TestLoaderImpl(tf))
                .forEach(tl -> tl.run());
    }
}
