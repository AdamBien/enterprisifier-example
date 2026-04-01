package com.enterprise.greeting.infrastructure.persistence;

import com.enterprise.greeting.domain.events.DomainEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * In-memory implementation of {@link GreetingEventStore}.
 * Events are stored in-process. Durability across restarts is deferred
 * to a future infrastructure sprint that will likely never be scheduled.
 */
public final class DefaultGreetingEventStoreImpl implements GreetingEventStore {

    private static final Logger LOG = Logger.getLogger(DefaultGreetingEventStoreImpl.class.getName());

    private final Map<String, List<DomainEvent>> eventLog = new HashMap<>();
    private long totalCount = 0;

    DefaultGreetingEventStoreImpl() {}

    @Override
    public void append(DomainEvent event) {
        eventLog.computeIfAbsent(event.getAggregateId(), k -> new ArrayList<>()).add(event);
        totalCount++;
        LOG.fine("[EventStore] Appended: " + event.getEventType() + " — total=" + totalCount);
    }

    @Override
    public List<DomainEvent> loadEvents(String aggregateId) {
        return new ArrayList<>(eventLog.getOrDefault(aggregateId, new ArrayList<>()));
    }

    @Override
    public long totalEventCount() { return totalCount; }
}
