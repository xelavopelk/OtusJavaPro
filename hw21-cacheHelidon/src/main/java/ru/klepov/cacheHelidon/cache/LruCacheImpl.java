package ru.klepov.cacheHelidon.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class LruCacheImpl<K, V> implements LruCache<K, V> {
    private final int capacity;
    private final Map<K, Node<CacheItem<K, V>>> map;
    private final DoublyLinkedList<CacheItem<K, V>> queue;
    private Boolean isValid; //флаг того, что кеш не развалился

    public LruCacheImpl(Integer capacity) {
        this.capacity = capacity;
        map = new HashMap<>(capacity, 1.0f);
        queue = new DoublyLinkedList<>();
        isValid = true;
    }

    private boolean ejectLast() {
        if (size() < 1) {
            return true;
        }
        var res = queue.popLast();
        if (res.isEmpty()) {
            return false;
        } else {
            var k = res.get().getItem().getKey();
            if (!map.containsKey(k)) {
                return false;
            } else {
                map.remove(k);
                return true;
            }
        }
    }

    public Boolean checkValid() {
        return isValid;
    }

    @Override
    public List<CacheItem<K, V>> dump() {
        return queue.toList();
    }

    @Override
    public Boolean set(K key, V value) {
        if (map.containsKey(key)) {
            Node<CacheItem<K, V>> node = map.get(key);
            var movedNode = queue.remove(node);
            if (movedNode.isPresent()) {
                var item = movedNode.get().getItem();
                item.setValue(value);
                var newNode = queue.push(item);
                map.put(key, newNode);
            } else {
                isValid = false;
                throw new IllegalStateException(String.format("Can't store %s", key));
            }
        } else {
            if (size() >= capacity) {
                isValid = ejectLast();
            }
            if (!isValid) {
                return false;
            }
            CacheItem<K, V> item = new CacheItem<>(key, value);
            var node = queue.push(item);
            map.put(key, node);
        }
        return true;

    }

    @Override
    public Optional<V> get(K key) {
        if (map.containsKey(key)) {
            Node<CacheItem<K, V>> node = map.get(key);
            var movedNode = queue.remove(node);
            if (movedNode.isPresent()) {
                var newNode = queue.push(movedNode.get().getItem());
                map.put(key, newNode);
                return Optional.of(node.getItem().getValue());
            } else {
                isValid = false;
                throw new IllegalStateException(String.format("Can't get %s", key));
            }
        }
        return Optional.empty();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public void clear() {
        map.clear();
        queue.clear();
    }
}
