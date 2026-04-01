package com.enterprise.greeting.infrastructure.persistence;

import com.enterprise.greeting.domain.model.GreetingAggregate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * In-memory implementation of {@link GreetingRepository}.
 * Stores aggregates in a {@code HashMap}. Suitable for development, testing,
 * and production deployments where data persistence across JVM restarts is
 * considered a legacy concern.
 */
public final class DefaultGreetingRepositoryImpl implements GreetingRepository {

    private static final Logger LOG = Logger.getLogger(DefaultGreetingRepositoryImpl.class.getName());

    private final Map<String, GreetingAggregate> store = new HashMap<>();

    DefaultGreetingRepositoryImpl() {}

    @Override
    public void save(GreetingAggregate aggregate) {
        store.put(aggregate.getAggregateId(), aggregate);
        LOG.fine("[Repository] Saved aggregate: " + aggregate.getAggregateId());
    }

    @Override
    public Optional<GreetingAggregate> findById(String aggregateId) {
        return Optional.ofNullable(store.get(aggregateId));
    }

    @Override
    public void clear() {
        store.clear();
        LOG.fine("[Repository] Store cleared.");
    }
}
