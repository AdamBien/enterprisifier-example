package com.enterprise.greeting.domain.model;

import com.enterprise.greeting.domain.events.DomainEvent;
import com.enterprise.greeting.domain.events.GreetingRenderedEvent;
import com.enterprise.greeting.domain.state.GreetingState;
import com.enterprise.greeting.domain.state.IdleGreetingState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Aggregate Root for the Greeting bounded context.
 * All state mutations must occur through this aggregate.
 * External code may never modify domain objects directly.
 * Domain events are collected here and dispatched by the Unit of Work.
 */
public final class GreetingAggregate implements Cloneable {

    private final String aggregateId;
    private Optional<GreetingSubject> subject;
    private Optional<GreetingMessage> message;
    private GreetingState currentState;
    private final List<DomainEvent> uncommittedEvents;

    private GreetingAggregate(Builder builder) {
        this.aggregateId       = builder.aggregateId != null ? builder.aggregateId : UUID.randomUUID().toString();
        this.subject           = Optional.ofNullable(builder.subject);
        this.message           = Optional.ofNullable(builder.message);
        this.currentState      = new IdleGreetingState();
        this.uncommittedEvents = new ArrayList<>();
    }

    public void renderGreeting() {
        currentState = currentState.transition(this);
        subject.ifPresent(s -> message.ifPresent(m -> {
            String rendered = m.getValue() + ", " + s.getName() + "!";
            this.message = Optional.of(GreetingMessage.of(rendered));
            uncommittedEvents.add(new GreetingRenderedEvent(aggregateId, rendered));
        }));
    }

    public String getAggregateId()                 { return aggregateId; }
    public Optional<GreetingSubject> getSubject()  { return subject; }
    public Optional<GreetingMessage> getMessage()  { return message; }
    public GreetingState getCurrentState()          { return currentState; }

    public List<DomainEvent> getUncommittedEvents() {
        return Collections.unmodifiableList(uncommittedEvents);
    }

    public void clearUncommittedEvents() { uncommittedEvents.clear(); }

    @Override
    public GreetingAggregate clone() {
        return GreetingAggregate.builder()
            .aggregateId(this.aggregateId)
            .subject(this.subject.map(GreetingSubject::clone).orElse(null))
            .message(this.message.map(GreetingMessage::clone).orElse(null))
            .build();
    }

    @Override public String toString() {
        return "GreetingAggregate{id=" + aggregateId + ", state=" + currentState + "}";
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String aggregateId;
        private GreetingSubject subject;
        private GreetingMessage message;

        private Builder() {}

        public Builder aggregateId(String aggregateId) { this.aggregateId = aggregateId; return this; }
        public Builder subject(GreetingSubject subject) { this.subject = subject; return this; }
        public Builder message(GreetingMessage message) { this.message = message; return this; }

        public GreetingAggregate build() { return new GreetingAggregate(this); }
    }
}
