package com.enterprise.greeting.application.mapper;

import java.util.Optional;

/**
 * Generic mapper contract for cross-boundary object translation.
 * Every layer boundary requires a dedicated mapper. Passing raw objects
 * across boundaries is a critical architectural violation.
 *
 * @param <S> the source type (from the calling layer)
 * @param <T> the target type (for the receiving layer)
 */
public interface Mapper<S, T> {

    /**
     * Maps the source object to the target type.
     *
     * @param source the object to map; must not be {@code null}
     * @return an Optional of the mapped target; empty if mapping produces no result
     */
    Optional<T> map(S source);
}
