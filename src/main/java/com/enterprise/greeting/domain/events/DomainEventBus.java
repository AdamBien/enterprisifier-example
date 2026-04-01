package com.enterprise.greeting.domain.events;

/**
 * Inbound port for the domain event bus.
 * No component may publish or subscribe to events by any other means.
 * All cross-cutting concerns (audit, metrics, saga triggers) subscribe here.
 */
public interface DomainEventBus {

    /**
     * Publishes a domain event to all registered subscribers.
     * Publication is synchronous within the same Unit of Work.
     *
     * @param event the domain event to publish; must not be {@code null}
     */
    void publish(DomainEvent event);

    /**
     * Registers a subscriber to receive all events of a given type.
     *
     * @param eventType  the fully-qualified event type string
     * @param subscriber the subscriber to invoke upon event publication
     */
    void subscribe(String eventType, DomainEventSubscriber subscriber);
}
