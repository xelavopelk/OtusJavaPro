package ru.otus.klepov.homeworks.hw10;

public class MoneyPackFactoryImpl implements MoneyPackFactory {
    private Integer maxnominal;
    private Integer maxcount;

    public MoneyPackFactoryImpl(Integer maxnominal, Integer maxcount) {
        this.maxnominal = maxnominal;
        this.maxcount = maxcount;
    }

    @Override
    public MoneyPack create(Integer nominal, Integer count) {
        if (count < 0 || nominal < 0 || count > maxcount || nominal > maxnominal) {
            throw new IllegalArgumentException(
                    String.format("И как вот с этим (count=%d, nominal=%d) создать пачку денег?", count, nominal)
            );
        } else {
            return new MoneyPackImpl(nominal, count);
        }
    }

    @Override
    public MoneyPack create(MoneyPack[] packs) throws IllegalArgumentException {
        if (packs.length < 0) {
            throw new IllegalArgumentException("Из ничего нельзя создать пачку денежек!");
        }
        var nominal = packs[0].getNominal();
        var count = packs[0].getCount();
        for (var i = 1; i < packs.length; i++) {
            if (nominal != packs[i].getNominal()) {
                throw new IllegalArgumentException(
                        String.format("Денежки разных номиналов (%d,%d) нельзя собрать в одну пачку", nominal, packs[i].getNominal())
                );
            } else {
                count += packs[i].getCount();
            }
        }
        return create(nominal, count);
    }
}
