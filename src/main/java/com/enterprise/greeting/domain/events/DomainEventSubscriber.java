package com.enterprise.greeting.domain.events;

/**
 * Observer contract for domain event subscribers.
 * Implement this interface to react to domain events without coupling
 * the producer to the consumer.
 */
@FunctionalInterface
public interface DomainEventSubscriber {

    /**
     * Called when a domain event matching the subscriber's registered type is published.
     *
     * @param event the published event; guaranteed non-null
     */
    void onEvent(DomainEvent event);
}
