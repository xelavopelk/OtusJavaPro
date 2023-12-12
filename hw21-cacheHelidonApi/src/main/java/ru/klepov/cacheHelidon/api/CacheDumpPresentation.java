package ru.klepov.cacheHelidon.api;

import lombok.Getter;
import lombok.Setter;
import ru.klepov.cacheHelidon.cache.CacheItem;

import java.util.List;

@Getter
@Setter
public class CacheDumpPresentation {
    private List<CacheItem<String, String>> list;
}
