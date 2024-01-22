package ru.klepov.hw35;


import java.util.concurrent.locks.ReentrantReadWriteLock;
/**
* Правки к условию задачи: из обучательного чата в телеге:
* Я: Как сравнивать Number?
* Чередник: Ограничений нет. Можно даже вообще убрать шаблон и использовать только double.
*/
public final class MedianList {
    private final NonUniqueTreeMap<Double> left;
    private final NonUniqueTreeMap<Double> right;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public MedianList() {
        left = new NonUniqueTreeMap<>();
        right = new NonUniqueTreeMap<>();
    }

    public int size() {
        lock.readLock().lock();
        try {
            return left.size() + right.size();
        } finally {
            lock.readLock().unlock();
        }
    }

    public void add(Double item) {
        lock.writeLock().lock();
        try {
            innerAdd(item);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void remove(Double item) {
        lock.writeLock().lock();
        try {
            innerRemove(item);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public double getMedian() {
        lock.readLock().lock();
        try {
            return innerGetMedian();
        } finally {
            lock.readLock().unlock();
        }
    }

    private void innerAdd(Double item) {
        if (0 == left.size() && left.size() == right.size()) {
            left.put(item);
        } else if (left.size() <= right.size()) {
            var r = right.getFirst().get();
            if (r > item) {
                left.put(item);
            } else {
                left.put(right.takeFirst().get());
                right.put(item);
            }
        } else {
            var l = left.getLast().get();
            if (l < item) {
                right.put(item);
            } else {
                right.put(left.takeLast().get());
                left.put(item);
            }
        }
    }

    private void innerRemove(Double item) {
        if (left.size() > 0 || right.size() > 0) {
            if (left.size() == 0) {
                right.remove(item);
            } else if (right.size() == 0) {
                left.remove(item);
            } else {
                var l = left.getLast().get();
                var r = right.getFirst().get();
                if (l == r && l == item) {
                    if (left.size() > right.size()) {
                        right.remove(item);
                    } else {
                        left.remove(item);
                    }
                } else if (l >= item) {
                    if (left.size() < right.size()) {
                        left.remove(item);
                        left.put(right.takeFirst().get());
                    } else {
                        left.remove(item);
                    }
                } else {
                    if (left.size() > right.size()) {
                        right.remove(item);
                        right.put(left.takeLast().get());
                    } else {
                        right.remove(item);
                    }
                }
            }
        }
    }

    private double innerGetMedian() {
        if (size() == 0) {
            return Double.NaN;
        } else {
            if (left.size() == right.size()) {
                var l = left.getLast();
                var r = right.getFirst();
                return ((l.orElse(0.0)) + (r.orElse(0.0))) / 2.0;
            } else if (left.size() > right.size()) {
                return left.getLast().get();
            } else {
                return right.getFirst().get();
            }
        }
    }

    Boolean checkConstraints() {
        return Math.max(left.size(), right.size()) - Math.min(left.size(), right.size()) <= 1;
    }
}