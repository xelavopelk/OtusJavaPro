package ru.otus.klepov.homeworks.hw10;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.klepov.homeworks.hw10.domain.MoneyPack;
import ru.otus.klepov.homeworks.hw10.domain.MoneyPackFactory;
import ru.otus.klepov.homeworks.hw10.domain.MoneyPackFactoryImpl;
import ru.otus.klepov.homeworks.hw10.domain.MoneyPackNominalComparator;
import ru.otus.klepov.homeworks.hw10.service.ChangeStrategyDpImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static ru.otus.klepov.homeworks.hw10.utils.makeList;

public class ChangeStrategyDpImplTest {
    private MoneyPackFactory mpf;

    @BeforeEach
    public void init(){
        mpf = new MoneyPackFactoryImpl(1000,10000);
    }

    @Test
    public void noboxFail() {
        var impl = new ChangeStrategyDpImpl(mpf);
        List<MoneyPack> data = new ArrayList<>();
        var res = impl.getChange(data, 6);
        assertTrue(res.isEmpty());
    }

    @Test
    public void negativeSumFail() {
        var impl = new ChangeStrategyDpImpl(mpf);
        var data = makeList(mpf.create(1,10), mpf.create(3,10), mpf.create(4,10) );
        var res = impl.getChange(data, -3);
        assertTrue(res.isEmpty());
    }

    @Test
    public void happyPath1Success() {
        var impl = new ChangeStrategyDpImpl(mpf);
        var data = makeList(mpf.create(1,10), mpf.create(3,10), mpf.create(4,10) );
        var res = impl.getChange(data, 6);
        assertTrue(res.isPresent());
    }

    @Test
    public void happyPath1BalanceSuccess() {
        var impl = new ChangeStrategyDpImpl(mpf);
        var data = makeList(mpf.create(1,10), mpf.create(3,10), mpf.create(4,10) );
        var res = impl.getChange(data, 6);
        assertArrayEquals(res.get().toArray(),
                new MoneyPack[] {mpf.create(3,2)});
    }

    @Test
    public void happyPath2Success() {
        var impl = new ChangeStrategyDpImpl(mpf);
        var data = makeList(mpf.create(3,10), mpf.create(8,10), mpf.create(9,10) );
        var res = impl.getChange(data, 16);
        assertTrue(res.isPresent());
    }

    @Test
    public void happyPath2BalanceSuccess() {
        var impl = new ChangeStrategyDpImpl(mpf);
        var data = makeList(mpf.create(3,10), mpf.create(8,10), mpf.create(9,10) );
        var res = impl.getChange(data, 16);
        assertArrayEquals(res.get().toArray(),
                new MoneyPack[] {mpf.create(8,2)});
    }

    @Test
    public void notEnough3Success() {
        var impl = new ChangeStrategyDpImpl(mpf);
        var data = makeList(mpf.create(1,10), mpf.create(3,1), mpf.create(4,10) );
        var mExpected = makeList(mpf.create(4,1),mpf.create(1,2));
        var res = impl.getChange(data, 6);
        var compareRes = res
                .get()
                .stream()
                .sorted(new MoneyPackNominalComparator().reversed())
                .collect(Collectors.toList());
        assertIterableEquals(compareRes,mExpected);
    }

    @Test
    public void failPath() {
        var impl = new ChangeStrategyDpImpl(mpf);
        var data = makeList(mpf.create(2,10), mpf.create(3,1), mpf.create(4,10) );
        var res = impl.getChange(data, 1);
        assertTrue(res.isEmpty());
    }

    @Test
    public void happyPath11Success() {
        var impl = new ChangeStrategyDpImpl(mpf);
        var data = makeList(mpf.create(3,10), mpf.create(8,10), mpf.create(9,10) );
        var mExpected = makeList(mpf.create(8,1),mpf.create(3,1));
        var res = impl.getChange(data, 11);
        var compareRes = res
                .get()
                .stream()
                .sorted(new MoneyPackNominalComparator().reversed())
                .collect(Collectors.toList());
        assertIterableEquals(compareRes,mExpected);
    }

    @Test
    public void happyPath15Success() {
        var impl = new ChangeStrategyDpImpl(mpf);
        var data = makeList(mpf.create(3,10), mpf.create(8,10), mpf.create(9,10) );
        var mExpected = makeList(mpf.create(9,1),mpf.create(3,2));
        var res = impl.getChange(data, 15);
        var compareRes = res
                .get()
                .stream()
                .sorted(new MoneyPackNominalComparator().reversed())
                .collect(Collectors.toList());
        assertIterableEquals(compareRes,mExpected);
    }

    @Test
    public void happyPath17Success() {
        var impl = new ChangeStrategyDpImpl(mpf);
        var data = makeList(mpf.create(3,10), mpf.create(8,10), mpf.create(9,10) );
        var mExpected = makeList(mpf.create(9,1),mpf.create(8,1));
        var res = impl.getChange(data, 17);
        var compareRes = res
                .get()
                .stream()
                .sorted(new MoneyPackNominalComparator().reversed())
                .collect(Collectors.toList());
        assertIterableEquals(compareRes,mExpected);
    }


}
