package ru.otus.klepov.homeworks.hw10;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyPackFactoryImplTest {
    private MoneyPackFactory mpf;

    @BeforeEach
    public void init() {
        mpf = new MoneyPackFactoryImpl(100, 10000);
    }

    @Test
    public void happyPathSuccess() {
        var res = mpf.create(new MoneyPack[]{mpf.create(3, 10), mpf.create(3, 15)});
        assertEquals(res, mpf.create(3, 25));
    }
}
