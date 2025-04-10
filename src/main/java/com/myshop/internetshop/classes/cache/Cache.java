package com.myshop.internetshop.classes.cache;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Cache<V> {
    private static final Logger logger = LoggerFactory.getLogger(Cache.class.getName());
    private final Map<String, CacheEntry<V>> cacheEntryMap = new HashMap<>();
    private static final long CACHE_EXPIRE_TIME = 30 * 60 * (long) 1000;
    private static final long CACHE_SIZE = 20;
    final ScheduledExecutorService scheduler;

    public Cache() {
        this.scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::cleanUp, CACHE_EXPIRE_TIME,
                CACHE_EXPIRE_TIME, TimeUnit.MILLISECONDS);
    }

    public void put(String key, V value) {
        if (cacheEntryMap.size() >= CACHE_SIZE) {
            removeOldest();
        }
        cacheEntryMap.put(key, new CacheEntry<>(value));
        logger.info("Put key: {} inside cache", key);
    }

    public boolean contains(final String key) {
        return cacheEntryMap.containsKey(key);
    }

    public V get(String key) {
        CacheEntry<V> entry = cacheEntryMap.get(key);
        if (entry != null) {
            entry.refresh();
            logger.info("Get key: {} inside cache", key);
            return entry.getValue();
        }
        return null;
    }

    private void cleanUp() {
        long now = System.currentTimeMillis();
        cacheEntryMap.entrySet().removeIf(
                entry -> now - entry.getValue().getTimestamp() > CACHE_EXPIRE_TIME);
        logger.info("Cache auto clean up complete");
    }

    private void removeOldest() {
        cacheEntryMap.entrySet().stream().min(Comparator.comparingLong(
            entry -> entry.getValue().getTimestamp())).ifPresent(entry -> {
                cacheEntryMap.remove(entry.getKey());
                logger.info("Removed oldest entry. Entry key: {}",
                       entry.getKey());
            });

    }

    public void remove(final String key) {
        cacheEntryMap.remove(key);
    }

    public void clear() {
        cacheEntryMap.clear();
        logger.info("Cache cleared");
    }


}
