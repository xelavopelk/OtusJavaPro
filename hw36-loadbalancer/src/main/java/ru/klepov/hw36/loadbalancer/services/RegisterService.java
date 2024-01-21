package ru.klepov.hw36.loadbalancer.services;

import org.springframework.stereotype.Service;
import ru.klepov.hw36.loadbalancer.entities.RegistryItem;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
public class RegisterService {
    private final ReentrantReadWriteLock lock;

    private final HashMap<String, RegistryItem> registry;
    private final RoundRobinList<RegistryItem> nexter;

    public RegisterService() {
        this.registry = new HashMap<>();
        nexter = new RoundRobinList<>();
        this.lock = new ReentrantReadWriteLock();
    }

    public RegistryItem register(String host) {
        lock.writeLock().lock();
        try {
            if (!registry.containsKey(host)) {
                var item = new RegistryItem(host, Instant.now(), 0);
                nexter.add(item);
                registry.put(host, item);
            }
            return registry.get(host);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void unregister(String host) {
        lock.writeLock().lock();
        try {
            if (registry.containsKey(host)) {
                nexter.remove(registry.get(host));
                registry.remove(host);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    public List<RegistryItem> getList() {
        lock.readLock().lock();
        try {
            return registry
                    .entrySet()
                    .stream()
                    .map(entry -> entry.getValue())
                    .toList();
        } finally {
            lock.readLock().unlock();
        }
    }

    public Optional<String> getNext() {
        lock.writeLock().lock();
        try {
            var res = nexter.getNext();
            if (res.isEmpty()) {
                return Optional.empty();
            } else {
                registry.get(res.get().getHost()).incrementCounter();
                return Optional.of(res.get().getHost());
            }
        } finally {
            lock.writeLock().unlock();
        }
    }
}
