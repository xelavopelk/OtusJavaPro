package ru.klepov.hw35;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleTests {
    @Test
    public void emptySizeSuccess() {
        var data = new MedianList();
        assertEquals(0, data.size());
    }

    @Test
    public void twoItemSizeSuccess() {
        var data = new MedianList();
        data.add(1.0);
        data.add(2.0);
        assertTrue(data.checkConstraints());
        assertEquals(2, data.size());
    }

    @Test
    public void twoItemOneRemainedSuccess() {
        var data = new MedianList();
        data.add(1.0);
        data.add(2.0);
        data.remove(1.0);
        assertTrue(data.checkConstraints());
        assertEquals(1, data.size());
    }

    @Test
    public void twoItemRemoveNotExistsSuccess() {
        var data = new MedianList();
        data.add(1.0);
        data.add(2.0);
        data.remove(5.0);
        assertTrue(data.checkConstraints());
        assertEquals(2, data.size());
    }

    @Test
    public void removeAllSuccess() {
        List<Double> test = Arrays.asList(new Double[]{1.0, 3.0, 5.0, 7.0, 10.0});
        var data = new MedianList();
        test.forEach(data::add);
        test.forEach(data::remove);
        assertTrue(data.checkConstraints());
        assertEquals(0, data.size());
    }

    @Test
    public void muchItemsSuccess() {
        var size = 10_000;
        var ml = new MedianList();
        var testData = Stream.generate(ThreadLocalRandom.current()::nextDouble)
                .limit(size)
                .toList();
        testData.forEach(ml::add);
        testData.stream().limit(size / 2).forEach(ml::remove);
        assertTrue(ml.checkConstraints());
        assertEquals(size - size / 2, ml.size());
    }

}
