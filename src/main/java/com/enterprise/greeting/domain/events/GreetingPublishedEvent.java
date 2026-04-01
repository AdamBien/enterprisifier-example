package com.enterprise.greeting.domain.events;

import java.time.Instant;
import java.util.Objects;

/**
 * Domain event fired once the greeting has been dispatched to the output adapter.
 * This marks the successful completion of the entire greeting saga.
 * Audit systems, metrics collectors, and downstream consumers subscribe to this event.
 */
public final class GreetingPublishedEvent implements DomainEvent {

    private static final String EVENT_TYPE = "com.enterprise.greeting.GreetingPublished.v1";

    private final String  aggregateId;
    private final String  outputChannel;
    private final Instant occurredOn;

    public GreetingPublishedEvent(String aggregateId, String outputChannel) {
        this.aggregateId   = Objects.requireNonNull(aggregateId);
        this.outputChannel = Objects.requireNonNull(outputChannel);
        this.occurredOn    = Instant.now();
    }

    @Override public String  getAggregateId() { return aggregateId; }
    @Override public Instant getOccurredOn()  { return occurredOn; }
    @Override public String  getEventType()   { return EVENT_TYPE; }

    public String getOutputChannel() { return outputChannel; }

    @Override public String toString() {
        return "GreetingPublishedEvent{aggregateId='" + aggregateId
             + "', outputChannel='" + outputChannel
             + "', occurredOn=" + occurredOn + "}";
    }
}
