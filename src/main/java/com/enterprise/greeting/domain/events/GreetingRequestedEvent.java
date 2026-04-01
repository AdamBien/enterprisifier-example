package com.enterprise.greeting.domain.events;

import java.time.Instant;
import java.util.Objects;

/**
 * Domain event fired when a greeting operation is requested.
 * Downstream saga orchestrators and read-model projectors subscribe to this event.
 */
public final class GreetingRequestedEvent implements DomainEvent {

    private static final String EVENT_TYPE = "com.enterprise.greeting.GreetingRequested.v1";

    private final String aggregateId;
    private final String requestedSubject;
    private final Instant occurredOn;

    public GreetingRequestedEvent(String aggregateId, String requestedSubject) {
        this.aggregateId      = Objects.requireNonNull(aggregateId);
        this.requestedSubject = Objects.requireNonNull(requestedSubject);
        this.occurredOn       = Instant.now();
    }

    @Override public String  getAggregateId()    { return aggregateId; }
    @Override public Instant getOccurredOn()     { return occurredOn; }
    @Override public String  getEventType()      { return EVENT_TYPE; }

    public String getRequestedSubject() { return requestedSubject; }

    @Override public String toString() {
        return "GreetingRequestedEvent{aggregateId='" + aggregateId
             + "', requestedSubject='" + requestedSubject
             + "', occurredOn=" + occurredOn + "}";
    }
}
