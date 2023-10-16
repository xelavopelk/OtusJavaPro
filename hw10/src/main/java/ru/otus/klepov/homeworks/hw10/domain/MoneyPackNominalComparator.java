package ru.otus.klepov.homeworks.hw10.domain;

import java.util.Comparator;

public class MoneyPackNominalComparator implements Comparator<MoneyPack> {
    @Override
    public int compare(MoneyPack o1, MoneyPack o2) {
        return o1.getNominal().compareTo(o2.getNominal());
    }
}
