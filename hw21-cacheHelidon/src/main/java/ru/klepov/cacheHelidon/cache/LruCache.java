package ru.klepov.cacheHelidon.cache;

import java.util.List;
import java.util.Optional;

public interface LruCache<K, V> {
    Boolean set(K key, V value);

    Optional<V> get(K key);

    int size();

    void clear();

    Boolean checkValid();

    List<CacheItem<K, V>> dump();
}
