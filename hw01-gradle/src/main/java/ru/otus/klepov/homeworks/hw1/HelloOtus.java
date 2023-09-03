package ru.otus.klepov.homeworks.hw1;
import com.google.common.base.Joiner;


public class HelloOtus {
    public static void main(String[] args) {
        var joiner = Joiner.on(" ").skipNulls();
        var res = joiner.join("В","этом", "классе", "сделайте", "вызов", "какого-нибудь", "метода", "из", "guava");
        System.out.println(res);
    }
}
