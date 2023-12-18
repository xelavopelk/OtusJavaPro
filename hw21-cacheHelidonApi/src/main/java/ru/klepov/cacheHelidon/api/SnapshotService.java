package ru.klepov.cacheHelidon.api;

import ru.klepov.cacheHelidon.cache.CacheItem;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface SnapshotService {
    Optional<List<CacheItem<String, String>>> load();

    void save(List<CacheItem<String, String>> data) throws IOException;
}
