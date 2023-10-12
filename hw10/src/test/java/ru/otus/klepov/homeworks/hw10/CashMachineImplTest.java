package ru.otus.klepov.homeworks.hw10;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class CashMachineImplTest {
    private ChangeStrategyGreedyImpl strat;
    private MoneyPackFactory mpf;
    private MoneyBoxFactory mbf;

    @BeforeEach
    public void init() {
        mpf=new MoneyPackFactoryImpl(100,10000);
        mbf=new MoneyBoxFactoryImpl();
        strat = new ChangeStrategyGreedyImpl();
    }
    private CashMachine happyPathPrepare() {
        var machine = new CashMachineImpl(strat, mpf, mbf);
        var data = utils.makeList(mpf.create(3,10), mpf.create(3,15 ));
        machine.putMoney(data);

        data = new ArrayList<>();
        data.add(mpf.create(1,10));
        machine.putMoney(data);
        return machine;
    }
    @Test
    public void happyPathBalance(){
        var machine = happyPathPrepare();
        machine.getMoney(10);
        assertEquals(75, machine.getBalance());
    }
    @Test
    public void happyPathBalanceMoney(){
        var machine = happyPathPrepare();
        var res = machine.getMoney(10);
        var mExpected = utils.makeList(mpf.create(3,3), mpf.create(1,1));
        assertTrue(res.isPresent());
        var compareRes = res
                .get()
                .stream()
                .sorted(new MoneyPackNominalComparator().reversed())
                .collect(Collectors.toList());
        assertIterableEquals(compareRes, mExpected);
    }

    @Test
    public void notEnoughMoney(){
        var machine = happyPathPrepare();
        var res = machine.getMoney(110);
        assertEquals(Optional.empty(), res);
        assertEquals(85, machine.getBalance());
    }

    @Test
    public void emptySum(){
        var machine = new CashMachineImpl(strat, mpf, mbf);
        assertEquals(machine.getBalance(), 0);
    }

    @Test
    public void oncePutMoney(){
        var machine = new CashMachineImpl(strat, mpf, mbf);
        var data = utils.makeList(mpf.create(3,10), mpf.create(3,15 ));
        machine.putMoney(data);
        assertEquals(75, machine.getBalance());
    }

    @Test
    public void twicePutMoney(){
        var machine = new CashMachineImpl(strat, mpf, mbf);
        var data = utils.makeList(mpf.create(3,10), mpf.create(3,15 ));
        machine.putMoney(data);

        data = new ArrayList<>();
        data.add(mpf.create(1,10));
        machine.putMoney(data);
        assertEquals(85, machine.getBalance());
    }
}
