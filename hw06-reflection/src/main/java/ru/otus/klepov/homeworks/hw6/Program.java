package ru.otus.klepov.homeworks.hw6;

import java.util.Arrays;



public class Program {
    public static void main(String[] args) {
        Arrays.asList("SimpleJUnitTest", "NoTestSimpleJUnitTest")
                .stream()
                .map(TestCaseFactoryImpl::new)
                .map(TestLoaderImpl::new)
                .forEach(TestLoaderImpl::run);
    }
}
