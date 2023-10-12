package ru.otus.klepov.homeworks.hw6;
import java.lang.annotation.*;

/**
 * Выполняется перед тестом. Использовать для класса. Аннотация доживает до рантайма.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Before {
}
