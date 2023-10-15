package ru.otus.klepov.homeworks.hw10;

public class MoneyBoxImpl implements MoneyBox {
    private final Integer nominal;
    private Integer count = 0;

    public MoneyBoxImpl(Integer nominal) {
        this.nominal = nominal;
    }

    public MoneyBoxImpl(Integer nominal, Integer count) {
        this.nominal = nominal;
        this.count = count;
    }

    public MoneyBoxImpl(MoneyPack pack) {
        this.nominal = pack.getNominal();
        this.count = pack.getCount();
    }

    @Override
    public void putMoney(MoneyPack notes) throws IllegalArgumentException {

        if (nominal != notes.getNominal()) {
            throw new IllegalArgumentException(
                    String.format("Тут лежат денежки номиналом %d, а суют %d", nominal, notes.getNominal())
            );
        }
        this.count += notes.getCount();
    }

    @Override
    public void getMoney(Integer notes) throws IndexOutOfBoundsException {
        if (this.count < notes) {
            throw new IndexOutOfBoundsException(
                    String.format("В коробочке меньше денежек (%d) нежели ты хочешь (%d)", this.count, notes));
        } else {
            this.count -= notes;
        }
    }

    @Override
    public Integer getCount() {
        return this.count;
    }

    @Override
    public Integer getNominal() {
        return this.nominal;
    }

    @Override
    public Integer getSumma() {
        return this.nominal * this.count;
    }
}
