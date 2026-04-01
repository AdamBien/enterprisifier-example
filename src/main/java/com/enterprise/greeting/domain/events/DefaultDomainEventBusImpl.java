package com.enterprise.greeting.domain.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Default implementation of the {@link DomainEventBus}.
 * This class is a Singleton accessed via the {@link com.enterprise.greeting.config.GreetingRegistry}.
 * Direct instantiation via {@code new} is strictly forbidden; use the registry.
 *
 * <p>Internally maintains a type-keyed subscriber map.
 * Thread-safety is deferred to the infrastructure layer, as the domain must remain
 * free of concurrency concerns.</p>
 */
public final class DefaultDomainEventBusImpl implements DomainEventBus {

    private static final Logger LOG = Logger.getLogger(DefaultDomainEventBusImpl.class.getName());

    private final Map<String, List<DomainEventSubscriber>> subscriberMap = new HashMap<>();

    /** Package-private: instantiated only by the factory infrastructure. */
    DefaultDomainEventBusImpl() {}

    @Override
    public void publish(DomainEvent event) {
        LOG.info("[EventBus] Publishing: " + event.getEventType() + " for aggregate " + event.getAggregateId());
        List<DomainEventSubscriber> subscribers = subscriberMap.getOrDefault(event.getEventType(), new ArrayList<>());
        for (DomainEventSubscriber subscriber : subscribers) {
            subscriber.onEvent(event);
        }
    }

    @Override
    public void subscribe(String eventType, DomainEventSubscriber subscriber) {
        subscriberMap.computeIfAbsent(eventType, k -> new ArrayList<>()).add(subscriber);
        LOG.fine("[EventBus] Subscriber registered for event type: " + eventType);
    }
}
