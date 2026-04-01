package com.enterprise.greeting.domain.state;

import com.enterprise.greeting.domain.model.GreetingAggregate;

/**
 * Transient processing state. The aggregate enters this state while the
 * greeting strategy is being applied. Transitions to {@link CompletedGreetingState}.
 * Any exception during processing must transition to {@link FailedGreetingState}.
 */
public final class ProcessingGreetingState implements GreetingState {

    @Override
    public GreetingState transition(GreetingAggregate aggregate) {
        return new CompletedGreetingState();
    }

    @Override
    public String getStateName() { return "PROCESSING"; }

    @Override public String toString() { return "ProcessingGreetingState"; }
}
