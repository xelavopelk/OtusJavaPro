package ru.otus.klepov.homeworks.hw6;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class TestCaseImpl implements TestCase {
    private ArrayList<Method> beforeMethods;
    private Method test;
    private ArrayList<Method> afterMethods;
    private Class<?> testClass;
    public TestCaseImpl(ArrayList<Method> before, Method test, ArrayList<Method> after, Class testClass) {
        this.beforeMethods=before;
        this.test = test;
        this.afterMethods = after;
        this.testClass = testClass;
    }
    public void run() throws InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {
        var obj = testClass.getDeclaredConstructor(new Class<?>[]{}).newInstance();
        for (var m : this.beforeMethods) {
            m.invoke(obj);
        }
        this.test.invoke(obj);
        for (var m : this.afterMethods) {
            m.invoke(obj);
        }
    }
    public String getName() {
        return test.getName();
    }
}
