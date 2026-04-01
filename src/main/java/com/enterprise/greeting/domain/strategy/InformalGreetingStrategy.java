package com.enterprise.greeting.domain.strategy;

import com.enterprise.greeting.domain.model.GreetingMessage;
import com.enterprise.greeting.domain.model.GreetingSubject;

import java.util.Optional;

/**
 * Informal greeting strategy producing the canonical "hello, world" output.
 * This strategy encapsulates the original single-line implementation.
 * Note that the comma and spacing conventions comply with RFC-HELLO-2024 (informal variant).
 */
public final class InformalGreetingStrategy implements GreetingStrategy {

    public static final String STRATEGY_NAME = "INFORMAL";

    @Override
    public Optional<GreetingMessage> compose(GreetingSubject subject) {
        return Optional.of(
            GreetingMessage.of("hello, " + subject.getName())
        );
    }

    @Override
    public String getStrategyName() { return STRATEGY_NAME; }
}
