package ru.klepov.cacheHelidon.cache;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Node<T> {
    private T item;
    private Node<T> previous;
    private Node<T> next;

    public Node(T item) {
        this.item = item;
        this.previous = null;
        this.next = null;
    }
}
