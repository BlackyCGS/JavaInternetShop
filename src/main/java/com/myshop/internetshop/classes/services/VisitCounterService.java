package com.myshop.internetshop.classes.services;


import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class VisitCounterService {

    private final Map<String, AtomicInteger> urlVisits = new ConcurrentHashMap<>();

    public void incrementUrlVisits(String url) {
        urlVisits.computeIfAbsent(url, k -> new AtomicInteger(0)).incrementAndGet();
    }

    public int getUrlVisitsByUrl(String url) {
        return urlVisits.getOrDefault(url, new AtomicInteger(0)).get();
    }

    public Map<String, Integer> getTotalVisits() {
        return urlVisits.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().get()));
    }
}
