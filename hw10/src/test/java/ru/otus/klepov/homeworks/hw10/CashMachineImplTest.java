package ru.otus.klepov.homeworks.hw10;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.klepov.homeworks.hw10.domain.*;
import ru.otus.klepov.homeworks.hw10.service.CashMachine;
import ru.otus.klepov.homeworks.hw10.service.CashMachineImpl;
import ru.otus.klepov.homeworks.hw10.service.ChangeStrategyGreedyImpl;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class CashMachineImplTest {
    private ChangeStrategyGreedyImpl strategy;
    private MoneyPackFactory mpf;
    private MoneyBoxFactory mbf;
    private static int MAX_NOMINAL = 100;
    private static int MAX_COUNT = 10_000;

    @BeforeEach
    public void init() {
        mpf = new MoneyPackFactoryImpl(MAX_NOMINAL, MAX_COUNT);
        mbf = new MoneyBoxFactoryImpl();
        strategy = new ChangeStrategyGreedyImpl();
    }

    private CashMachine happyPathPrepare() {
        var machine = new CashMachineImpl(strategy, mpf, mbf);
        var data = utils.makeList(mpf.create(3, 10), mpf.create(3, 15));
        machine.putMoney(data);
        data = new ArrayList<>();
        data.add(mpf.create(1, 10));
        machine.putMoney(data);
        return machine;
    }

    @Test
    public void happyPathBalanceSuccess() {
        var machine = happyPathPrepare();
        machine.getMoney(10);
        assertEquals(75, machine.getBalance());
    }

    @Test
    public void happyPathBalanceMoneySuccess() {
        var machine = happyPathPrepare();
        var res = machine.getMoney(10);
        var mExpected = utils.makeList(mpf.create(3, 3), mpf.create(1, 1));
        var compareRes = res
                .get()
                .stream()
                .sorted(new MoneyPackNominalComparator().reversed())
                .collect(Collectors.toList());
        assertIterableEquals(compareRes, mExpected);
    }

    @Test
    public void notEnoughMoneySuccess() {
        var machine = happyPathPrepare();
        var res = machine.getMoney(110);
        assertEquals(85, machine.getBalance());
    }

    @Test
    public void notEnoughMoneyNotChangedBalanceSuccess() {
        var machine = happyPathPrepare();
        machine.getMoney(110);
        assertEquals(85, machine.getBalance());
    }

    @Test
    public void emptySumSuccess() {
        var machine = new CashMachineImpl(strategy, mpf, mbf);
        assertEquals(machine.getBalance(), 0);
    }

    @Test
    public void oncePutMoneySuccess() {
        var machine = new CashMachineImpl(strategy, mpf, mbf);
        var data = utils.makeList(mpf.create(3, 10), mpf.create(3, 15));
        machine.putMoney(data);
        assertEquals(75, machine.getBalance());
    }

    @Test
    public void twicePutMoneySuccess() {
        var machine = new CashMachineImpl(strategy, mpf, mbf);
        var data = utils.makeList(mpf.create(3, 10), mpf.create(3, 15));
        machine.putMoney(data);
        data = new ArrayList<>();
        data.add(mpf.create(1, 10));
        machine.putMoney(data);
        assertEquals(85, machine.getBalance());
    }
}
