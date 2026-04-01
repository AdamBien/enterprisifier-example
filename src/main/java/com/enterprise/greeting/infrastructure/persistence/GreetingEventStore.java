package com.enterprise.greeting.infrastructure.persistence;

import com.enterprise.greeting.domain.events.DomainEvent;

import java.util.List;

/**
 * Event store interface for Event Sourcing support.
 * All domain events are appended to this store in the order they occurred.
 * The aggregate state can be fully reconstructed by replaying all events
 * for a given aggregate ID — a process that is critical for the 0.001% of cases
 * where the in-memory repository is insufficient.
 */
public interface GreetingEventStore {

    /**
     * Appends a domain event to the store.
     *
     * @param event the event to append; must not be {@code null}
     */
    void append(DomainEvent event);

    /**
     * Loads all events for the given aggregate in chronological order.
     *
     * @param aggregateId the aggregate identifier
     * @return an ordered list of all events; never {@code null}
     */
    List<DomainEvent> loadEvents(String aggregateId);

    /**
     * Returns the total number of events stored across all aggregates.
     */
    long totalEventCount();
}
