package ru.otus.klepov.homeworks.hw10;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChangeStrategyImplTest {

    private MoneyPackFactory mpf;

    @BeforeEach
    public void init() {
        mpf = new MoneyPackFactoryImpl(1000, 10000);
    }

    @Test
    public void happyPathSuccess() {
        var impl = new ChangeStrategyGreedyImpl();
        var data = utils.makeList(mpf.create(1, 10), mpf.create(3, 10), mpf.create(4, 10));
        var res = impl.getChange(data, 15);
        assertTrue(res.isPresent());
        assertArrayEquals(res.get().toArray(),
                new MoneyPack[]{mpf.create(4, 3), mpf.create(3, 1)});
    }

    @Test
    public void negativePathFail() {
        var impl = new ChangeStrategyGreedyImpl();
        var data = utils.makeList(mpf.create(3, 10), mpf.create(4, 10));
        var res = impl.getChange(data, 2);
        assertTrue(res.isEmpty());
    }


    @Test
    public void negativePathNotEnoughMoney1Success() {
        var impl = new ChangeStrategyGreedyImpl();
        var data = utils.makeList(mpf.create(3, 2), mpf.create(4, 2));
        var res = impl.getChange(data, 21);
        assertTrue(res.isEmpty());
    }

    @Test
    public void negativePathNotEnoughMoney2Success() {
        var impl = new ChangeStrategyGreedyImpl();
        var data = utils.makeList(mpf.create(3, 10), mpf.create(4, 10));
        var res = impl.getChange(data, 2000);
        assertTrue(res.isEmpty());
    }
}
