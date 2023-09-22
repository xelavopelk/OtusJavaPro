package ru.otus.klepov.homeworks.hw6;

import java.lang.annotation.*;

/**
 * Функция-тест. Использовать для класса. Аннотация доживает до рантайма.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Test {
}
