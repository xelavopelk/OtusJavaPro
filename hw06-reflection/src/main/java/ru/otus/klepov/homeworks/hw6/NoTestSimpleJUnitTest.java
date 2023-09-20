package ru.otus.klepov.homeworks.hw6;

import ru.otus.klepov.homeworks.hw6.After;
import ru.otus.klepov.homeworks.hw6.Before;

public class NoTestSimpleJUnitTest {
    private int t = 0;
    @Before
    public void before() {
        t=0;
    }
    @Before
    public void before2() {
        t+=2;
    }
    @After
    public void after() {
        t=1000;
    }

}
