package ru.otus.klepov.homeworks.hw3;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Box<T extends Fruit> {
    private ArrayList<T> data = new ArrayList<T>();
    public Box(){
    }
    public Box(List<T> fruits){
        data.addAll(fruits);
    }
    public void add(T fruit) {
        data.add(fruit);
    }
    public void add(Box<T> anotherBox) {
        if (anotherBox!=null && !anotherBox.equals(this)) {
            this.data.addAll(anotherBox.data);
            anotherBox.data = new ArrayList<T>();
        }
    }
    public BigDecimal weight() {
        BigDecimal res= BigDecimal.ZERO;
        for(var item : data){
            res=res.add(item.getWeight());
        }
        return res;
    }

    public boolean compare(Box<? extends Fruit> o) {
        return 0==weight().compareTo(o.weight());
    }

}
