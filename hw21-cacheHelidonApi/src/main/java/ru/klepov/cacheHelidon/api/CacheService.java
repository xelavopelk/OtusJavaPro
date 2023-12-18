package ru.klepov.cacheHelidon.api;

import io.helidon.webserver.http.HttpRules;
import io.helidon.webserver.http.HttpService;
import io.helidon.webserver.http.ServerRequest;
import io.helidon.webserver.http.ServerResponse;
import jakarta.json.JsonObject;
import ru.klepov.cacheHelidon.cache.LruCache;


public class CacheService implements HttpService {

    private LruCache<String, String> cache;

    CacheService(LruCache<String, String> cache) {
        this.cache = cache;
    }

    @Override
    public void routing(HttpRules rules) {
        rules.get("/{key}", this::getItem)
                .put("/{key}", this::putItem);
    }

    private void getItem(ServerRequest request,
                         ServerResponse response) {
        String key = request.path().pathParameters().get("key");
        var res = cache.get(key);
        if (res.isEmpty()) {
            response.send(404);
        } else {
            response.send(res.get());
        }
    }

    private void putItem(ServerRequest request,
                         ServerResponse response) {
        String key = request.path().pathParameters().get("key");
        var jo = request.content().as(JsonObject.class);
        cache.set(key, jo.toString());
        response.send(key);
    }
}
