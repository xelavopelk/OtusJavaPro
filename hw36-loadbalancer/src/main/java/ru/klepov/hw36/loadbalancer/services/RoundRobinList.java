package ru.klepov.hw36.loadbalancer.services;

import java.util.ArrayList;
import java.util.Optional;

public class RoundRobinList<T> {
    private ArrayList<T> list;
    private Integer pointer;

    public RoundRobinList() {
        pointer = 0;
        list = new ArrayList<>();
    }

    public Optional<T> getNext() {
        if (list.isEmpty()) {
            return Optional.empty();
        } else {
            var current = Optional.of(list.get(pointer));
            if (pointer + 1 == list.size()) {
                pointer = 0;
            } else {
                pointer++;
            }
            return current;
        }
    }

    public void add(T item) {
        if (item == null || list.contains(item)) {
            return;
        }
        list.add(item);
    }

    public void remove(T item) {
        if (null != item) {
            var itemIndex = list.indexOf(item);
            if (itemIndex == pointer) {
                var nextPointer = (pointer == list.size() - 1 ? 0 : pointer);
                list.remove(itemIndex);
                pointer = nextPointer;
            } else if (itemIndex >= 0) {
                list.remove(itemIndex);
                if (pointer >= itemIndex) {
                    pointer = Math.max(pointer - 1, 0);
                }
            }
        }
    }
}
