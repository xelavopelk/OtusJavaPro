package ru.klepov.hw36.loadbalancer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.klepov.hw36.loadbalancer.services.RoundRobinList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RoundRobinTest {
    @Test
    void emptyTestSuccess() {
        var rl = new RoundRobinList<Integer>();
        assertEquals(Optional.empty(), rl.getNext());
    }

    @Test
    void emptyTestRoundSuccess() {
        var rl = new RoundRobinList<Integer>();
        var data = Arrays.asList(1, 2, 3, 4, 5);
        data.forEach(rl::add);
        var res = new ArrayList<Integer>();
        for (var i = 0; i < 7; i++) {
            res.add(rl.getNext().get());
        }
        Assertions.assertIterableEquals(Arrays.asList(1, 2, 3, 4, 5, 1, 2), res);
    }

    @Test
    void emptyTestRemove1Success() {
        var rl = new RoundRobinList<Integer>();
        var data = Arrays.asList(1, 2, 3, 4, 5);
        data.forEach(rl::add);
        var res = new ArrayList<Integer>();
        res.add(rl.getNext().get());
        rl.remove(2);
        res.add(rl.getNext().get());
        res.add(rl.getNext().get());
        Assertions.assertIterableEquals(Arrays.asList(1, 3, 4), res);
    }

    @Test
    void emptyTestRemove2Success() {
        var rl = new RoundRobinList<Integer>();
        var data = Arrays.asList(1, 2, 3, 4, 5);
        data.forEach(rl::add);
        var res = new ArrayList<Integer>();
        res.add(rl.getNext().get());
        rl.remove(3);
        res.add(rl.getNext().get());
        res.add(rl.getNext().get());
        Assertions.assertIterableEquals(Arrays.asList(1, 2, 4), res);
    }

    @Test
    void emptyTestRemoveAllSuccess() {
        var rl = new RoundRobinList<Integer>();
        var data = Arrays.asList(1, 2, 3, 4, 5);
        data.forEach(rl::add);
        var res = new ArrayList<Integer>();
        res.add(rl.getNext().get());
        data.forEach(rl::remove);
        Assertions.assertIterableEquals(Arrays.asList(1), res);
        assertEquals(Optional.empty(), rl.getNext());
    }

    @Test
    void emptyTestRemoveFirstSuccess() {
        var rl = new RoundRobinList<Integer>();
        var data = Arrays.asList(1, 2, 3, 4, 5);
        data.forEach(rl::add);
        var res = new ArrayList<Integer>();
        rl.remove(1);
        res.add(rl.getNext().get());
        res.add(rl.getNext().get());
        res.add(rl.getNext().get());
        res.add(rl.getNext().get());
        res.add(rl.getNext().get());
        Assertions.assertIterableEquals(Arrays.asList(2, 3, 4, 5, 2), res);
    }

}
