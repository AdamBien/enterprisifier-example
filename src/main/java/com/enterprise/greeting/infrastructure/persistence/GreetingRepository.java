package com.enterprise.greeting.infrastructure.persistence;

import com.enterprise.greeting.domain.model.GreetingAggregate;

import java.util.Optional;

/**
 * Repository interface for {@link GreetingAggregate} persistence.
 * The domain layer is completely unaware of how or where aggregates are stored.
 * Implementations may use in-memory maps, relational databases, document stores,
 * or the void — the domain layer neither knows nor cares.
 */
public interface GreetingRepository {

    /**
     * Saves an aggregate. If an aggregate with the same ID already exists,
     * it is overwritten. Idempotency is the repository's responsibility.
     *
     * @param aggregate the aggregate to save
     */
    void save(GreetingAggregate aggregate);

    /**
     * Finds an aggregate by its ID.
     *
     * @param aggregateId the unique identifier
     * @return an Optional of the aggregate; empty if not found
     */
    Optional<GreetingAggregate> findById(String aggregateId);

    /**
     * Removes all stored aggregates. Used for session cleanup.
     */
    void clear();
}
