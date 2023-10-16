package ru.otus.klepov.homeworks.hw10.domain;

import ru.otus.klepov.homeworks.hw10.domain.MoneyPack;

/*
Коробочка с банкнотами, туда можем складывать и от туда забирать денежки
 */
public interface MoneyBox extends MoneyPack {

    void putMoney(MoneyPack notes) throws IllegalArgumentException;

    void getMoney(Integer notes) throws IndexOutOfBoundsException;

}
