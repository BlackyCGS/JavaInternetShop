package com.myshop.internetshop.classes.services;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;



@Service
public class VisitCounterService {
        private final Map<String, AtomicInteger> urlVisits = new ConcurrentHashMap<>();

    public void incrementUrlVisits(String url) {
        urlVisits.computeIfAbsent(url, k -> new AtomicInteger(0)).incrementAndGet();
    }

    public Map<String, Integer> getTotalVisits() {
        return urlVisits.entrySet()
                .stream()
                .collect(Collectors
                .toMap(Map.Entry::getKey, e -> e.getValue().get()));
    }
}
