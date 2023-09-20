package ru.otus.klepov.homeworks.hw3;

import java.math.BigDecimal;

public abstract class Fruit {
    private BigDecimal weight;
    public Fruit(BigDecimal weight) {
        this.weight=weight;
    }
    public BigDecimal getWeight() {
        return weight;
    }
}
