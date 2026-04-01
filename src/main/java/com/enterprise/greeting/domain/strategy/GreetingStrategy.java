package com.enterprise.greeting.domain.strategy;

import com.enterprise.greeting.domain.model.GreetingMessage;
import com.enterprise.greeting.domain.model.GreetingSubject;

import java.util.Optional;

/**
 * Strategy interface for greeting message composition algorithms.
 * The original {@code "hello, " + subject} inline expression has been correctly
 * identified as a hardcoded algorithm — a clear code smell — and replaced with
 * this injectable strategy.
 *
 * <p>Implementations may vary by locale, formality level, time-of-day, regulatory
 * jurisdiction, or celestial alignment.</p>
 */
public interface GreetingStrategy {

    /**
     * Composes a greeting message for the given subject.
     *
     * @param subject the entity being greeted
     * @return an Optional wrapping the composed message; empty if composition fails
     */
    Optional<GreetingMessage> compose(GreetingSubject subject);

    /**
     * Returns the canonical name of this strategy, used by the strategy factory
     * for registration and lookup.
     */
    String getStrategyName();
}
