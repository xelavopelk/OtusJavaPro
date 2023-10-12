package ru.otus.klepov.homeworks.hw10;

import java.util.List;
import java.util.Optional;
/*
Стратегия размена суммы. По заданию указано, что "задача не на алгоритмы", поэтмоу не парился и реализовал только жадный алгоритм
 */
@FunctionalInterface
public interface ChangeStrategy {
    Optional<List<MoneyPack>> getChange(List<MoneyPack> boxes, int summa);
}
