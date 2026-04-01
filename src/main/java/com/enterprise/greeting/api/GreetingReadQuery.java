package com.enterprise.greeting.api;

import java.util.Optional;

/**
 * CQRS Read side: query interface for retrieving previously rendered greetings.
 * The read model must never share a repository with the write model.
 * Projections are rebuilt from the event store and served through this interface.
 */
public interface GreetingReadQuery {

    /**
     * Finds the most recently rendered greeting for the given correlation identifier.
     *
     * @param correlationId the correlation ID assigned at command issuance
     * @return an Optional of the read-side projection DTO
     */
    Optional<GreetingResponseDTO> findByCorrelationId(String correlationId);

    /**
     * Returns all greetings that were processed in the current session.
     *
     * @return an iterable projection collection; never {@code null}
     */
    Iterable<GreetingResponseDTO> findAll();
}
