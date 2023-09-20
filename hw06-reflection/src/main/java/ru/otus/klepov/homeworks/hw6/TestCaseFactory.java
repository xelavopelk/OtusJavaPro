package ru.otus.klepov.homeworks.hw6;

import ru.otus.klepov.homeworks.hw6.TestCase;

import java.util.ArrayList;

public interface TestCaseFactory {
    String getClassName();
    ArrayList<TestCase> getTestCases();
}
