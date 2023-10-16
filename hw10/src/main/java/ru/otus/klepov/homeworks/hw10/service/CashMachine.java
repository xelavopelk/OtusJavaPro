package ru.otus.klepov.homeworks.hw10.service;

import ru.otus.klepov.homeworks.hw10.domain.MoneyPack;

import java.util.List;
import java.util.Optional;

public interface CashMachine {
    void putMoney(List<MoneyPack> notes);

    Optional<List<MoneyPack>> getMoney(Integer summa);

    Integer getBalance();
}
