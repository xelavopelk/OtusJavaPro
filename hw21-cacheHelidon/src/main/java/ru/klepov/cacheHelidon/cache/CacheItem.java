package ru.klepov.cacheHelidon.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CacheItem<K, V> {
    private K key;
    private V value;

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        } else {
            CacheItem<K, V> tmp = (CacheItem<K, V>) obj;
            return tmp.getKey().equals(this.getKey()) && tmp.getValue().equals(this.getValue());
        }
    }
}
