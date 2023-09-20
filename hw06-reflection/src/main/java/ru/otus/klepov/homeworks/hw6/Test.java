package ru.otus.klepov.homeworks.hw6;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)     // чтобы использовать для класса
@Retention(RetentionPolicy.RUNTIME)  // хотим чтобы наша аннотация дожила до рантайма
public @interface Test {
}
