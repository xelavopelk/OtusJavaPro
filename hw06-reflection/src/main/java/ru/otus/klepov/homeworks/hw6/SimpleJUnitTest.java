package ru.otus.klepov.homeworks.hw6;

public class SimpleJUnitTest {
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
    @Test
    public void test() {
        t++;
    }
    @Test
    public void testEx() {
        t++;
        throw new RuntimeException("Ой больно мне больно!");
    }

    public static int testStatic() {
        return 1;
    }
}
