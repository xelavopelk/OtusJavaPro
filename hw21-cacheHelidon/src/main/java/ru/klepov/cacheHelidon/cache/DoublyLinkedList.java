package ru.klepov.cacheHelidon.cache;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public final class DoublyLinkedList<T> {
    private Node<T> head;
    private Node<T> tail;

    public DoublyLinkedList() {
        head = null;
        tail = null;
    }

    public Node<T> pushLast(T item) {
        var node = new Node<T>(item);
        if (head == null) {
            head = node;
            tail = node;
            head.setNext(null);
            head.setPrevious(null);
        } else {
            tail.setNext(node);
            node.setPrevious(tail);
            tail = node;
            tail.setNext(null);
        }
        return node;
    }

    public Node<T> push(T item) {
        var node = new Node<T>(item);
        if (head == null) {
            head = node;
            tail = node;
            head.setNext(null);
            head.setPrevious(null);
        } else {
            head.setPrevious(node);
            node.setNext(head);
            head = node;
            head.setPrevious(null);
        }
        return node;
    }

    public Optional<Node<T>> popLast() {
        if ((head == null && tail == null)) {
            return Optional.empty();
        } else if (head == tail) {
            var res = head;
            head = null;
            tail = null;
            return Optional.of(res);
        } else {
            var res = tail;
            var newTail = tail.getPrevious();
            newTail.setNext(null);
            res.setPrevious(null);
            tail = newTail;
            return Optional.of(res);
        }
    }

    public Optional<Node<T>> pop() {
        if ((head == null && tail == null)) {
            return Optional.empty();
        } else if (head == tail) {
            var res = head;
            head = null;
            tail = null;
            return Optional.of(res);
        } else {
            var res = head;
            var newHead = head.getNext();
            newHead.setPrevious(null);
            res.setNext(null);
            head = newHead;
            return Optional.of(res);
        }
    }

    public Optional<Node<T>> remove(Node<T> item) {
        if (item.getNext() == null && item.getPrevious() == null) {
            return Optional.empty();
        }
        //remove head
        else if (item.getNext() != null && item.getPrevious() == null) {
            return pop();
        }
        //remove tail
        else if (item.getNext() == null && item.getPrevious() != null) {
            return popLast();
        } else {
            var left = item.getPrevious();
            var right = item.getNext();
            left.setNext(right);
            right.setPrevious(left);
            item.setNext(null);
            item.setPrevious(null);
            return Optional.of(item);
        }
    }

    public List<T> toList() {
        var res = new ArrayList<T>();
        if (head == null && tail == null) {
            return res;
        } else {
            var current = head;
            while (current != null) {
                res.add(current.getItem());
                current = current.getNext();
            }
            return res;
        }
    }

    public void clear() {
        head = null;
        tail = null;
    }
}
