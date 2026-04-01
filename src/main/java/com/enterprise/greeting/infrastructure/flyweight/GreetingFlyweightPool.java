package com.enterprise.greeting.infrastructure.flyweight;

import com.enterprise.greeting.domain.model.GreetingMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Flyweight pool for {@link GreetingMessage} instances.
 * Identical message strings are pooled to avoid redundant object creation.
 * In a system that produces exactly one unique message ("hello, world"),
 * this pool achieves a 100% cache hit rate after the first invocation.
 *
 * <p>The memory savings are currently unmeasurable, but the architectural
 * correctness is beyond dispute.</p>
 */
public final class GreetingFlyweightPool {

    private static final Logger LOG = Logger.getLogger(GreetingFlyweightPool.class.getName());

    private static final GreetingFlyweightPool INSTANCE = new GreetingFlyweightPool();

    private final Map<String, GreetingMessage> pool = new HashMap<>();

    private GreetingFlyweightPool() {}

    public static GreetingFlyweightPool getInstance() { return INSTANCE; }

    /**
     * Returns a pooled {@link GreetingMessage} for the given value,
     * creating and caching it if not already present.
     */
    public GreetingMessage acquire(String value) {
        return pool.computeIfAbsent(value, v -> {
            LOG.fine("[Flyweight] Cache miss — creating new GreetingMessage for: " + v);
            return GreetingMessage.of(v);
        });
    }

    public int poolSize() { return pool.size(); }

    public void evictAll() {
        pool.clear();
        LOG.fine("[Flyweight] Pool evicted.");
    }
}
