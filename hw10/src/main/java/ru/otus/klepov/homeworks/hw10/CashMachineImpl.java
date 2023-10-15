package ru.otus.klepov.homeworks.hw10;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class CashMachineImpl implements CashMachine {
    private final ChangeStrategy changeStrategy;
    private final MoneyPackFactory mpf;
    private final MoneyBoxFactory mbf;
    private final Map<Integer, MoneyBox> moneyBoxes;

    public CashMachineImpl(ChangeStrategy changeStrategy, MoneyPackFactory mpf, MoneyBoxFactory mbf) {
        this.changeStrategy = changeStrategy;
        this.moneyBoxes = new HashMap<>();
        this.mpf = mpf;
        this.mbf = mbf;
    }

    @Override
    public void putMoney(List<MoneyPack> notes) {
        for (var item : notes) {
            moneyBoxes
                    .merge(item.getNominal(), new MoneyBoxImpl(item), (oldV, newV)
                            -> new MoneyBoxImpl(mpf.create(new MoneyPack[]{oldV, newV})));
        }
    }

    @Override
    public Optional<List<MoneyPack>> getMoney(Integer summa) {
        var change = changeStrategy.getChange(moneyBoxes.values().stream().map(mbf::Create).collect(Collectors.toList()), summa);
        if (change.isEmpty()) {
            return Optional.empty();
        } else {
            for (var item : change.get()) {
                moneyBoxes.get(item.getNominal()).getMoney(item.getCount());
            }
            return change;
        }
    }

    @Override
    public Integer getBalance() {
        var res = 0;
        for (var item : moneyBoxes.values()) {
            res += item.getSumma();
        }
        return res;
    }
}
