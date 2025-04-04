package com.myshop.internetshop.classes.cache;

import lombok.Getter;

@Getter
public class CacheEntry<V> {
    private final V value;
    private long timestamp;

    public CacheEntry(V value) {
        this.value = value;
        this.timestamp = System.currentTimeMillis();
    }

    public void refresh() {
        this.timestamp = System.currentTimeMillis();
    }
}
