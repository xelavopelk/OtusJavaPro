package ru.otus.klepov.homeworks.hw3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
public class BoxTest {
    @Test
    void bothZero() {
        var box1 = new Box<Apple>();
        var box2 = new Box<Apple>();
        assertEquals(box1.weight(),box2.weight());
    }
    @Test
    void notEqual() {
        var box1 = new Box<Apple>();
        var apples = new ArrayList<Apple>();
        apples.add(new Apple(BigDecimal.valueOf(1.0)));
        var box2 = new Box<Apple>(apples);
        assertNotEquals(box1.weight(),box2.weight());
    }

    @Test
    void equalDiffFruits() {
        var oranges = new ArrayList<Orange>();
        oranges.add(new Orange(BigDecimal.valueOf(1.1)));
        oranges.add(new Orange(BigDecimal.valueOf(1.2)));
        var box1 = new Box<Orange>(oranges);
        var apples = new ArrayList<Apple>();
        apples.add(new Apple(BigDecimal.valueOf(1.1)));
        apples.add(new Apple(BigDecimal.valueOf(1.2)));
        var box2 = new Box<Apple>(apples);
        assertEquals(box1.weight(),box2.weight());
    }

    @Test
    void notEqualDiffFruits() {
        var oranges = new ArrayList<Orange>();
        oranges.add(new Orange(BigDecimal.valueOf(2.3)));
        var box1 = new Box<Orange>(oranges);
        var apples = new ArrayList<Apple>();
        apples.add(new Apple(BigDecimal.valueOf(1.1)));
        apples.add(new Apple(BigDecimal.valueOf(1.21)));
        var box2 = new Box<Apple>(apples);
        assertNotEquals(box1.weight(),box2.weight());
    }
    @Test
    void addFromBox() {
        var apples1 = new ArrayList<Apple>();
        apples1.add(new Apple(BigDecimal.valueOf(2.3)));
        var box1 = new Box<Apple>(apples1);
        var apples2 = new ArrayList<Apple>();
        apples2.add(new Apple(BigDecimal.valueOf(1.1)));
        apples2.add(new Apple(BigDecimal.valueOf(1.21)));
        var box2 = new Box<Apple>(apples2);
        box1.add(box2);
        assertTrue(box1.weight().compareTo(BigDecimal.valueOf(4.61))==0
                && box2.weight().compareTo(BigDecimal.ZERO)==0);
    }

}
