package ru.klepov.hw35;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@State(Scope.Benchmark)
public class MedianBenchmark {

    private final int treeSize = 5_000_000;
    private MedianList ml;
    private List<Double> testData;

    @Setup
    public void setup() {
        ml = new MedianList();
        testData = Stream.generate(ThreadLocalRandom.current()::nextDouble)
                .limit(treeSize)
                .toList();
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void testAddRemove(Blackhole bh) {
        testData.forEach(ml::add);
        testData.stream().limit(treeSize / 2).forEach((item) -> {
            ml.getMedian();
            ml.remove(item);
        });
    }

}
