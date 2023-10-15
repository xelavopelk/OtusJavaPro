package ru.otus.klepov.homeworks.hw10;

/*
Пачка денег. пачка из одной банкноты это и есть банкнота. Перевязана резикой и меняться не может.
 */
public interface MoneyPack {
    Integer getCount();

    Integer getSumma();

    Integer getNominal();
}
