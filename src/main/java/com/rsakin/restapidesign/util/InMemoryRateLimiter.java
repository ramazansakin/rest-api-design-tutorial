package com.rsakin.restapidesign.util;

import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class InMemoryRateLimiter {

    private final Map<String, AtomicInteger> counters = new ConcurrentHashMap<>();
    // can be configured on properties and can be dynamic
    private final int limit = 10;
    private final Duration duration = Duration.ofMinutes(1);


    public boolean tryAcquire(String key) {
        AtomicInteger counter = counters.computeIfAbsent(key, k -> new AtomicInteger(0));
        int currentCount = counter.incrementAndGet();

        // Schedule a task to reset the counter after the rate limit duration
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(() -> counter.set(0), duration.toMillis(), TimeUnit.MILLISECONDS);

        return currentCount <= limit;
    }

}