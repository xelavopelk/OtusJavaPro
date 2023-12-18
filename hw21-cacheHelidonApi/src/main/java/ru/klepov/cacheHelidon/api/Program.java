package ru.klepov.cacheHelidon.api;

import io.helidon.common.context.Context;
import io.helidon.common.context.Contexts;
import io.helidon.config.Config;
import io.helidon.logging.common.HelidonMdc;
import io.helidon.logging.common.LogConfig;
import io.helidon.openapi.OpenApiFeature;
import io.helidon.scheduling.Scheduling;
import io.helidon.webserver.WebServer;
import io.helidon.webserver.WebServerConfig;
import io.helidon.webserver.http.HttpRouting;
import ru.klepov.cacheHelidon.cache.LruCache;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;


public class Program {
    private final static String MAX_LOST_TIME_PARAM = "max-lost-time";
    private final static String FILE_DUMP_PARAM = "file-dump";
    private final static String CACHE_CAPACITY_PARAM = "capacity";
    private static final Logger LOGGER = Logger.getLogger(Program.class.getName());

    private Program() {
    }

    /**
     * Application entry point.
     */
    public static void main(final String[] args) {
        LogConfig.configureRuntime();
        // the Helidon context is used to propagate MDC across threads
        // if running within Helidon WebServer, you do not need to runInContext, as that is already
        // done by the webserver
        Contexts.runInContext(Context.create(), Program::logging);
        Config config = Config.create();
        Config.global(config);

        var snapshotSrv = new SnapshotServiceImpl(config.get("cache").get(FILE_DUMP_PARAM).asString().get());
        var cf = new CacheFactory(snapshotSrv, config.get("cache").get(CACHE_CAPACITY_PARAM).asInt().get());
        var lruCache = cf.create(LOGGER::warning);
        if (lruCache.isPresent()) {
            var cache = new CacheService(lruCache.get());
            WebServerConfig.Builder builder = WebServer.builder();
            setupScheduling(lruCache.get(), snapshotSrv, config);
            setupWebserver(builder, cache, config);
            WebServer server = builder.build().start();
            LOGGER.info("LRU cache server is up! http://localhost:" + server.port() + "/openapi");
        } else {
            LOGGER.warning("Program finished. Corrupted dump!");
        }
    }

    static void setupWebserver(WebServerConfig.Builder server, CacheService cache, Config config) {
        server
                .config(config.get("server"))
                .addFeature(OpenApiFeature.create())
                .routing(routing -> Program.routing(routing, cache));
    }

    static void setupScheduling(LruCache<String, String> cache, SnapshotService sSrv, Config config) {
        var secs = config.get("cache").get(MAX_LOST_TIME_PARAM).asInt().get();
        Scheduling.fixedRateBuilder()
                .delay(secs)
                .task(inv -> {
                    LOGGER.info(String.format("DUMP CACHE Every %d seconds", secs));
                    sSrv.save(cache.dump());
                    LOGGER.info(String.format("DUMP SUCCESSFULL", secs));
                })
                .build();
    }

    static void routing(HttpRouting.Builder routing, CacheService cache) {
        routing
                //проект тренировочный, эту шляпу я прикрутил, чтобы знать на будущее как трассировать запросы в роутинге
                .any((req, res) -> {
                    LOGGER.info("Request: " + req.path());
                    res.next();
                })
                .register("/cache", cache);
    }

    private static void logging() {
        HelidonMdc.set("name", "startup");
        LOGGER.info("Starting up");

        // now let's see propagation across executor service boundary
        HelidonMdc.set("name", "propagated");
        // wrap executor so it supports Helidon context, this is done for all built-in executors in Helidon
        ExecutorService es = Contexts.wrap(Executors.newSingleThreadExecutor());

        Future<?> submit = es.submit(() -> {
            LOGGER.info("Running on another thread");
        });
        try {
            submit.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        es.shutdown();
    }

}
