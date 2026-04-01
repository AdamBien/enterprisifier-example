package com.enterprise.greeting.domain.events;

import java.time.Instant;

/**
 * Marker interface for all domain events in the Greeting bounded context.
 * Every mutation to the aggregate root must result in at least one domain event.
 * Events are immutable records of what happened; they must never be modified after creation.
 */
public interface DomainEvent {

    /**
     * Returns the unique identifier of the aggregate that produced this event.
     */
    String getAggregateId();

    /**
     * Returns the point in time at which this event occurred.
     * Time is always UTC. Local time zones are an infrastructure concern.
     */
    Instant getOccurredOn();

    /**
     * Returns the fully-qualified event type name, used by the event store
     * to reconstruct the type during event sourcing replay.
     */
    String getEventType();
}
