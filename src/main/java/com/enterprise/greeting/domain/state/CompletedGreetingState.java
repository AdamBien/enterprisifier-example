package com.enterprise.greeting.domain.state;

import com.enterprise.greeting.domain.model.GreetingAggregate;

/**
 * Terminal success state. The greeting has been fully rendered and published.
 * No further transitions are possible from this state.
 * Attempts to transition out will throw an {@link IllegalStateException},
 * correctly propagated through all 17 layers.
 */
public final class CompletedGreetingState implements GreetingState {

    @Override
    public GreetingState transition(GreetingAggregate aggregate) {
        throw new IllegalStateException(
            "GreetingAggregate[" + aggregate.getAggregateId() + "] is already in COMPLETED state. " +
            "A completed greeting cannot be re-greeted. Consider initiating a new GreetingAggregate " +
            "through the AbstractGreetingStrategyFactoryFactory.");
    }

    @Override
    public String getStateName() { return "COMPLETED"; }

    @Override public String toString() { return "CompletedGreetingState"; }
}
