package ru.otus.klepov.homeworks.hw6;

import java.util.ArrayList;

public interface TestCaseFactory {
    String getClassName();
    ArrayList<TestCase> getTestCases();
}
