import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.klepov.cacheHelidon.cache.CacheItem;
import ru.klepov.cacheHelidon.cache.LruCache;
import ru.klepov.cacheHelidon.cache.LruCacheImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class LruCacheImplTest {

    private LruCache<String,String> cache;
    private Integer DEFAULT_CAPACITY = 4;
    @BeforeEach
    public void init() {
        cache=new LruCacheImpl<String,String>(DEFAULT_CAPACITY);
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
    public void happyPath3itemsFails() {
        cache.set("key1", "test1");
        cache.set("key2", "test2");
        cache.set("key2", "test3");
        assertTrue(cache.checkValid());
        assertNotEquals(cache.size(),3);
    }
    @Test
    public void happyPath2itemsWUpdateSuccess() {
        cache.set("key1", "test1");
        cache.set("key2", "test2");
        cache.set("key2", "test3");
        assertTrue(cache.checkValid());
        assertEquals(cache.size(),2);
    }
    @Test
    public void happyPathMoreAsCapacitySuccess() {
        cache.set("key1", "test1");
        cache.set("key2", "test2");
        cache.set("key3", "test3");
        cache.set("key4", "test4");
        assertTrue(cache.checkValid());
        assertEquals(cache.size(),DEFAULT_CAPACITY);
    }
    @Test
    public void happyPathMoreThanCapacitySuccess() {
        cache.set("key1", "test1");
        cache.set("key2", "test2");
        cache.set("key3", "test3");
        cache.set("key4", "test4");
        cache.set("key5", "test5");
        assertTrue(cache.checkValid());
        assertEquals(cache.size(),DEFAULT_CAPACITY);
    }

    @Test
    public void doubleSetSuccess() {
        cache.set("key1", "test1");
        cache.set("key2", "test2");
        cache.set("key1", "test4");
        assertTrue(cache.checkValid());
        assertEquals(cache.size(),2);
    }

    @Test
    public void put2itemsSuccess() {
        cache.set("key1", "test1");
        cache.set("key2", "test2");
        List<CacheItem<String,String>> sample = new ArrayList<CacheItem<String,String>>();
        sample.add(new CacheItem<>("key1", "test1"));
        sample.add(new CacheItem<>("key2", "test2"));
        assertIterableEquals(cache.dump(), sample.reversed());;
        assertTrue(cache.checkValid());
    }

    @Test
    public void put2itemsAndGetSuccess() {
        cache.set("key1", "test1");
        cache.set("key2", "test2");
        assertEquals(cache.size(),2);
        assertEquals(cache.get("key1"), Optional.of("test1"));
        assertEquals(cache.get("key2"), Optional.of("test2"));

        List<CacheItem<String,String>> sample = new ArrayList<CacheItem<String,String>>();
        sample.add(new CacheItem<>("key1", "test1"));
        sample.add(new CacheItem<>("key2", "test2"));
        assertIterableEquals(cache.dump(), sample.reversed());;

        assertTrue(cache.checkValid());
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
