package com.enterprise.greeting.domain.events;

import java.time.Instant;
import java.util.Objects;

/**
 * Domain event fired once the greeting message has been fully rendered by the domain strategy.
 * The event store persists this event as the canonical source of truth for the rendered value.
 * Read-model projectors reconstruct the greeting view model by replaying events of this type.
 */
public final class GreetingRenderedEvent implements DomainEvent {

    private static final String EVENT_TYPE = "com.enterprise.greeting.GreetingRendered.v1";

    private final String aggregateId;
    private final String renderedMessage;
    private final Instant occurredOn;

    public GreetingRenderedEvent(String aggregateId, String renderedMessage) {
        this.aggregateId     = Objects.requireNonNull(aggregateId);
        this.renderedMessage = Objects.requireNonNull(renderedMessage);
        this.occurredOn      = Instant.now();
    }

    @Override public String  getAggregateId() { return aggregateId; }
    @Override public Instant getOccurredOn()  { return occurredOn; }
    @Override public String  getEventType()   { return EVENT_TYPE; }

    public String getRenderedMessage() { return renderedMessage; }

    @Override public String toString() {
        return "GreetingRenderedEvent{aggregateId='" + aggregateId
             + "', renderedMessage='" + renderedMessage
             + "', occurredOn=" + occurredOn + "}";
    }
}
