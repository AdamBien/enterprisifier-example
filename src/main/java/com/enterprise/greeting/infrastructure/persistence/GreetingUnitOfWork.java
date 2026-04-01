package com.enterprise.greeting.infrastructure.persistence;

import com.enterprise.greeting.domain.events.DomainEventBus;
import com.enterprise.greeting.domain.model.GreetingAggregate;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Unit of Work collecting all aggregate changes in a single transaction boundary.
 * On {@link #commit()}, all dirty aggregates are persisted and their uncommitted
 * domain events are published to the {@link DomainEventBus}.
 *
 * <p>This ensures atomicity: either all changes and events are committed, or none are.
 * In the current in-memory implementation, the atomicity guarantee is honoured
 * on a best-effort basis, which is the industry standard for hello-world applications.</p>
 */
public final class GreetingUnitOfWork {

    private static final Logger LOG = Logger.getLogger(GreetingUnitOfWork.class.getName());

    private final GreetingRepository repository;
    private final GreetingEventStore  eventStore;
    private final DomainEventBus      eventBus;
    private final List<GreetingAggregate> dirty = new ArrayList<>();

    public GreetingUnitOfWork(GreetingRepository repository,
                               GreetingEventStore eventStore,
                               DomainEventBus eventBus) {
        this.repository = repository;
        this.eventStore  = eventStore;
        this.eventBus    = eventBus;
    }

    public void register(GreetingAggregate aggregate) {
        dirty.add(aggregate);
    }

    public void commit() {
        LOG.info("[UnitOfWork] Committing " + dirty.size() + " dirty aggregate(s).");
        for (GreetingAggregate aggregate : dirty) {
            repository.save(aggregate);
            aggregate.getUncommittedEvents().forEach(event -> {
                eventStore.append(event);
                eventBus.publish(event);
            });
            aggregate.clearUncommittedEvents();
        }
        dirty.clear();
    }

    public void rollback() {
        LOG.warning("[UnitOfWork] Rolling back " + dirty.size() + " dirty aggregate(s).");
        dirty.clear();
    }
}
