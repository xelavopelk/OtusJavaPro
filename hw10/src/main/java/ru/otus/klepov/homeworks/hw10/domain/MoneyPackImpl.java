package ru.otus.klepov.homeworks.hw10.domain;

public class MoneyPackImpl implements MoneyPack {
    public final Integer nominal;
    public final Integer count;

    public MoneyPackImpl(Integer nominal, Integer count) {
        this.nominal = nominal;
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public Integer getNominal() {
        return nominal;
    }

    public Integer getSumma() {
        return nominal * count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoneyPackImpl that = (MoneyPackImpl) o;
        return this.getCount().equals(that.getCount()) &&
                this.getNominal().equals(that.getNominal());
    }
}
