package ru.otus.klepov.homeworks.hw10.domain;

/*
Бизнес-логика с констрейнтами по созданию пачки денег
 */
public interface MoneyPackFactory {
    MoneyPack create(Integer nominal, Integer count) throws IllegalArgumentException;

    MoneyPack create(MoneyPack[] packs) throws IllegalArgumentException;
}
