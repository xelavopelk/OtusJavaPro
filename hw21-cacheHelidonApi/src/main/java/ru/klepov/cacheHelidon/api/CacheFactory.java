package ru.klepov.cacheHelidon.api;

import ru.klepov.cacheHelidon.cache.LruCache;
import ru.klepov.cacheHelidon.cache.LruCacheImpl;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class CacheFactory {
    private SnapshotService sSrv;
    private Integer capacity;

    public CacheFactory(SnapshotService sSrv, Integer capacity) {
        this.sSrv = sSrv;
        this.capacity = capacity;
    }

    public Optional<LruCache<String, String>> create(Consumer<String> logger) {
        logger.accept("Start load cache backup");
        var res = new LruCacheImpl<String, String>(capacity);
        var data = sSrv.load().map(List::reversed);
        if (data.isPresent()) {
            for (var item : data.get()) {
                res.set(item.getKey(), item.getValue());
            }
            logger.accept(String.format("Finish load cache backup. Loaded items: %d", res.size()));
            return Optional.of(res);
        } else {
            logger.accept("No backup data");
            return Optional.empty();
        }
    }
}
