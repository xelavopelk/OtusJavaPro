package ru.otus.klepov.homeworks.hw10;

public class MoneyBoxFactoryImpl implements MoneyBoxFactory {

    @Override
    public MoneyBox Create(MoneyPack pack) {
        return new MoneyBoxImpl(pack);
    }
}
