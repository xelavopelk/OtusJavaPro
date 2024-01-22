package ru.klepov.hw35;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MedianTests {
    @Test
    void medianEmptySuccess() {
        var data = new MedianList();
        assertTrue(data.checkConstraints());
        assertEquals(Double.NaN, data.getMedian());
    }

    @Test
    void medianOneSuccess() {
        var data = new MedianList();
        data.add(1.0);
        assertTrue(data.checkConstraints());
        assertEquals(1.0, data.getMedian());
    }

    @Test
    void medianOneRightSuccess() {
        var data = new MedianList();
        data.add(1.0);
        data.add(2.0);
        data.remove(1.0);
        assertTrue(data.checkConstraints());
        assertEquals(2.0, data.getMedian());
    }

    @Test
    void medianOneLeftSuccess() {
        var data = new MedianList();
        data.add(1.0);
        data.add(2.0);
        data.remove(2.0);
        assertTrue(data.checkConstraints());
        assertEquals(1.0, data.getMedian());
    }

    @Test
    void median5ItemsSuccess() {
        var data = new MedianList();
        data.add(1.0);
        data.add(2.0);
        data.add(0.0);
        data.add(-1.0);
        data.add(1.5);
        assertTrue(data.checkConstraints());
        assertEquals(1.0, data.getMedian());
    }

    @Test
    void median5ItemsRemoveLeftSuccess() {
        var data = new MedianList();
        data.add(1.0);
        data.add(2.0);
        data.add(0.0);
        data.add(-1.0);
        data.add(1.5);
        data.remove(-1.0);
        assertTrue(data.checkConstraints());
        assertEquals(1.25, data.getMedian());
    }

    @Test
    void median5ItemsRemoveRightSuccess() {
        var data = new MedianList();
        data.add(1.0);
        data.add(2.0);
        data.add(0.0);
        data.add(-1.0);
        data.add(1.5);
        data.remove(1.5);
        assertTrue(data.checkConstraints());
        assertEquals(0.5, data.getMedian());
    }


    @Test
    void median4ItemsSuccess() {
        var data = new MedianList();
        data.add(1.0);
        data.add(2.0);
        data.add(-1.0);
        data.add(1.5);
        assertTrue(data.checkConstraints());
        assertEquals(1.25, data.getMedian());
    }

    @Test
    void median4ItemsNoRemoveSuccess() {
        var data = new MedianList();
        data.add(1.0);
        data.add(2.0);
        data.add(-1.0);
        data.remove(4.0);
        data.add(1.5);
        assertTrue(data.checkConstraints());
        assertEquals(1.25, data.getMedian());
    }

    @Test
    void median4ItemsRemoveSuccess() {
        var data = new MedianList();
        data.add(1.0);
        data.add(2.0);
        data.add(-1.0);
        data.remove(1.0);
        data.add(1.5);
        assertTrue(data.checkConstraints());
        assertEquals(1.5, data.getMedian());
    }


    @Test
    void median5Items1RepeatedSuccess() {
        var data = new MedianList();
        data.add(1.0);
        data.add(2.0);
        data.add(0.0);
        data.add(-1.0);
        data.add(2.0);
        assertTrue(data.checkConstraints());
        assertEquals(1.0, data.getMedian());
    }

    @Test
    void median4ItemsRepeatedSuccess() {
        var data = new MedianList();
        data.add(1.0);
        data.add(2.0);
        data.add(2.0);
        data.add(1.0);
        assertTrue(data.checkConstraints());
        assertEquals(1.5, data.getMedian());
    }

    @Test
    void median4ItemsAllRepeatedSuccess() {
        var data = new MedianList();
        data.add(3.0);
        data.add(3.0);
        data.add(3.0);
        data.add(3.0);
        assertTrue(data.checkConstraints());
        assertEquals(3.0, data.getMedian());
    }


}
