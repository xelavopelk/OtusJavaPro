package ru.klepov.cacheHelidon.cache;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentLruCacheImpl<K, V> implements LruCache<K, V> {
    private final LruCache<K, V> cache;
    private final ReentrantLock lock = new ReentrantLock();

    public ConcurrentLruCacheImpl(LruCache<K, V> cache) {
        this.cache = cache;
    }

    @Override
    public Boolean set(K key, V value) {
        lock.lock();
        try {
            return cache.set(key, value);
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public Optional<V> get(K key) {
        lock.lock();
        try {
            return cache.get(key);
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public int size() {
        lock.lock();
        try {
            return cache.size();
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public void clear() {
        lock.lock();
        try {
            cache.clear();
        } finally {
            this.lock.unlock();
        }
    }

    @Override
    public Boolean checkValid() {
        return cache.checkValid(); //тут осознанно забираем без локов
    }

    @Override
    public List<CacheItem<K, V>> dump() {
        lock.lock();
        try {
            return cache.dump();
        } finally {
            this.lock.unlock();
        }
    }
}
