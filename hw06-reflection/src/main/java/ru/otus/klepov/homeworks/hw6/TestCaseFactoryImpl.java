package ru.otus.klepov.homeworks.hw6;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;


public class TestCaseFactoryImpl implements TestCaseFactory {
    private String pkg;
    private String className;
   public TestCaseFactoryImpl(String className){
        this.className=className;
        this.pkg=this.getClass().getPackage().getName();
    }

    private Class getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "."+ className.substring(0, className.lastIndexOf('.')));
        } catch (Exception ex) {}
        return null;
    }
    private Class getCls() throws RuntimeException {
        InputStream stream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream(pkg.replaceAll("[.]", "/"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        var cls = reader.lines()
                .filter(line -> line.equals(className+".class"))
                .map(line -> getClass(line, pkg))
                .filter(Objects::nonNull)
                .limit(1)
                .collect(Collectors.toList());
        if (cls.size()!=1) {throw new RuntimeException("Shit happens!");}
        else {return cls.get(0);}
    }
    public String getClassName() {return className;}
    public ArrayList<TestCase> getTestCases() {
        var before = new ArrayList<Method>();
        var after = new ArrayList<Method>();
        var tests = new ArrayList<Method>();

        var cls = getCls();
        for (var meth: cls.getDeclaredMethods()) {
            if (Modifier.isPublic(meth.getModifiers())) {
                var ans = meth.getAnnotations();
                if (Arrays.stream(ans).anyMatch(an -> an instanceof After)) {
                    after.add(meth);
                } else if (Arrays.stream(ans).anyMatch(an -> an instanceof Before)) {
                    before.add(meth);
                } else if (Arrays.stream(ans).anyMatch(an -> an instanceof Test)) {
                    tests.add(meth);
                }
            }
        }
        var res = new ArrayList<TestCase>();
        for (var t: tests) {
            res.add(new TestCaseImpl(before,t,after, cls));
        }
        return res;
    }

}
