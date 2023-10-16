package ru.otus.klepov.homeworks.hw10.service;

import ru.otus.klepov.homeworks.hw10.domain.MoneyPack;
import ru.otus.klepov.homeworks.hw10.domain.MoneyPackImpl;

import java.util.*;
import java.util.stream.Collectors;

public class ChangeStrategyGreedyImpl implements ChangeStrategy {
    private Optional<List<MoneyPack>> getChangeInner(List<MoneyPack> boxes, int summa) {
        var outMoney = new ArrayList<MoneyPack>();
        var needMoney = summa;
        for (var i = 0; i < boxes.size(); i++) {
            var nominal = boxes.get(i).getNominal();
            Integer cow = needMoney / nominal;
            if (cow > boxes.get(i).getCount()) break;
            if (cow > 0) {
                var pack = new MoneyPackImpl(nominal, cow);
                outMoney.add(pack);
                needMoney -= pack.getSumma();
            }
            if (needMoney == 0) break;
        }
        return needMoney > 0 ? Optional.empty() : Optional.of(outMoney);
    }

    @Override
    public Optional<List<MoneyPack>> getChange(List<MoneyPack> boxes, int summa) {
        if (boxes.size() < 1 || summa < 1) {
            return Optional.empty();
        } else {
            var sorted = boxes
                    .stream()
                    .sorted(Comparator.comparingInt(MoneyPack::getNominal).reversed())
                    .collect(Collectors.toList());
            return getChangeInner(sorted, summa);
        }

    }
}
