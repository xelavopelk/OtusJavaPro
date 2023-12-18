import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.klepov.cacheHelidon.cache.ConcurrentLruCacheImpl;
import ru.klepov.cacheHelidon.cache.LruCache;
import ru.klepov.cacheHelidon.cache.LruCacheImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConcurrentLruCacheImplTest {
    private LruCache<String,String> cache;
    private Integer DEFAULT_CAPACITY = 4;
    @BeforeEach
    public void init() {
        cache=new ConcurrentLruCacheImpl<String,String>(new LruCacheImpl<String,String>(DEFAULT_CAPACITY));
    }

    @Test
    public void happyPath3itemsSuccess() {
        cache.set("key1", "test1");
        cache.set("key2", "test2");
        cache.set("key3", "test3");
        assertTrue(cache.checkValid());
        assertEquals(cache.size(),3);
    }

    @Test
    public void put3itemsAndGetSuccess() {
        cache.set("key1", "test1");
        cache.set("key2", "test2");
        cache.set("key3", "test3");
        cache.set("key3", "test4");
        assertEquals(cache.size(),3);
        assertEquals(Optional.of("test1"), cache.get("key1"));
        assertEquals(Optional.of("test2"), cache.get("key2"));
        assertEquals(Optional.of("test4"), cache.get("key3"));
        assertEquals(Optional.empty(), cache.get("key10"));
        assertTrue(cache.checkValid());
    }
}
