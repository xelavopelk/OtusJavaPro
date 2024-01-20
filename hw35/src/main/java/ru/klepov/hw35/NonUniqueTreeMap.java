package ru.klepov.hw35;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.function.Supplier;

//тримапа с дублями
public class NonUniqueTreeMap<T extends Number & Comparable> {
    private final TreeMap<T, Integer> tree;
    private volatile Integer size;

    public NonUniqueTreeMap() {
        tree = new TreeMap<>();
        size = 0;
    }

    public Integer size() {
        return size;
    }

    public void put(T item) {
        var v = tree.get(item);
        if (v != null) {
            tree.put(item, v + 1);
        } else {
            tree.put(item, 1);
        }
        size++;
    }

    private Optional<T> take(Supplier<Map.Entry<T, Integer>> selector) {
        if (tree.size() == 0) {
            return Optional.empty();
        } else {
            var v = selector.get();
            if (0 == v.getValue()) {
                return Optional.empty();
            } else {
                if (v.getValue() == 1) {
                    tree.remove(v.getKey());
                } else {
                    tree.put(v.getKey(), v.getValue() - 1);
                }
                size--;
                return Optional.of(v.getKey());
            }
        }
    }

    public void remove(T val) {
        Supplier<Map.Entry<T, Integer>> supplier = () -> {
            var r = tree.get(val);
            return new AbstractMap.SimpleEntry<T, Integer>(val, null == r ? 0 : r);
        };

        take(supplier);
    }

    public Optional<T> takeFirst() {
        return take(tree::firstEntry);
    }

    public Optional<T> takeLast() {
        return take(tree::lastEntry);
    }

    public Optional<T> getFirst() {
        if (tree.size() == 0) {
            return Optional.empty();
        } else {
            return Optional.of(tree.firstEntry().getKey());
        }
    }

    public Optional<T> getLast() {
        if (tree.size() == 0) {
            return Optional.empty();
        } else {
            return Optional.of(tree.lastEntry().getKey());
        }
    }
}
