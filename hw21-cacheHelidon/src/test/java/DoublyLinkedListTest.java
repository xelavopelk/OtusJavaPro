import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.klepov.cacheHelidon.cache.DoublyLinkedList;
import ru.klepov.cacheHelidon.cache.Node;
import org.junit.jupiter.api.Assertions;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DoublyLinkedListTest {

    @Test
    public void threeItemPushLastSuccess() {
        var sample = List.of(new Integer[] {1,2,3});
        var actual = new DoublyLinkedList<Integer>();
        for(var item: sample) {
            actual.push(item);
        }
        assertIterableEquals(actual.toList(),sample.reversed());
    }
    @Test
    public void threeItemPushSuccess() {
        var sample = List.of(new Integer[] {1,2,3});
        var actual = new DoublyLinkedList<Integer>();
        for(var item: sample) {
            actual.pushLast(item);
        }
        assertIterableEquals(actual.toList(),sample);
    }

    @Test
    public void pop1Success() {
        var sample = List.of(new Integer[] {1});
        var actual = new DoublyLinkedList<Integer>();
        for(var item: sample) {
            actual.pushLast(item);
        }
        actual.pop();
        assertIterableEquals(actual.toList(),List.of(new Integer[] {}));
    }
    @Test
    public void popLast1Success() {
        var sample = List.of(new Integer[] {1});
        var actual = new DoublyLinkedList<Integer>();
        for(var item: sample) {
            actual.pushLast(item);
        }
        actual.popLast();
        assertIterableEquals(actual.toList(),List.of(new Integer[] {}));
    }
    @Test
    public void popSuccess() {
        var sample = List.of(new Integer[] {1,2,3});
        var actual = new DoublyLinkedList<Integer>();
        for(var item: sample) {
            actual.pushLast(item);
        }
        actual.pop();
        assertIterableEquals(actual.toList(),List.of(new Integer[] {2,3}));
    }
    @Test
    public void popLastSuccess() {
        var sample = List.of(new Integer[] {1,2,3});
        var actual = new DoublyLinkedList<Integer>();
        for(var item: sample) {
            actual.pushLast(item);
        }
        actual.popLast();
        assertIterableEquals(actual.toList(),List.of(new Integer[] {1,2}));
    }

    @Test
    public void removeMiddleSuccess() {
        var sample = List.of(new Integer[] {1,2,3});
        var actual = new DoublyLinkedList<Integer>();
        for(var item: sample) {
            actual.pushLast(item);
        }
        var res = actual.remove(actual.getHead().getNext());
        assertTrue(res.isPresent());
        assertEquals(2, res.get().getItem());
        assertIterableEquals(actual.toList(),List.of(new Integer[] {1,3}));
    }

    @Test
    public void removeMiddleFails() {
        var sample = List.of(new Integer[] {1,2,3});
        var actual = new DoublyLinkedList<Integer>();
        for(var item: sample) {
            actual.pushLast(item);
        }
        var res = actual.remove(actual.getHead().getNext());
        assertTrue(res.isPresent());
        assertNotEquals(3, res.get().getItem());
        assertIterableEquals(actual.toList(),List.of(new Integer[] {1,3}));
    }
    @Test
    public void removeFirstSuccess() {
        var sample = List.of(new Integer[] {1,2,3});
        var actual = new DoublyLinkedList<Integer>();
        for(var item: sample) {
            actual.pushLast(item);
        }
        var res = actual.remove(actual.getHead());
        assertTrue(res.isPresent());
        assertEquals(1, res.get().getItem());
        assertIterableEquals(actual.toList(),List.of(new Integer[] {2,3}));
    }
    @Test
    public void removeLastSuccess() {
        var sample = List.of(new Integer[] {1,2,3});
        var actual = new DoublyLinkedList<Integer>();
        for(var item: sample) {
            actual.pushLast(item);
        }
        var res = actual.remove(actual.getTail());
        assertTrue(res.isPresent());
        assertEquals(3, res.get().getItem());
        assertIterableEquals(actual.toList(),List.of(new Integer[] {1,2}));
    }

    @Test
    public void removeNoLinked() {
        var sample = List.of(new Integer[] {1,2,3});
        var actual = new DoublyLinkedList<Integer>();
        for(var item: sample) {
            actual.pushLast(item);
        }
        var res = actual.remove(new Node<>(4));
        assertTrue(res.isEmpty());
        assertIterableEquals(actual.toList(),List.of(new Integer[] {1,2,3}));
    }

}
