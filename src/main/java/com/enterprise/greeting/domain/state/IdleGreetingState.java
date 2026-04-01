package com.enterprise.greeting.domain.state;

import com.enterprise.greeting.domain.model.GreetingAggregate;

/**
 * Initial state of the greeting lifecycle. The aggregate begins in this state.
 * Transitions to {@link ProcessingGreetingState} upon receipt of a render command.
 */
public final class IdleGreetingState implements GreetingState {

    @Override
    public GreetingState transition(GreetingAggregate aggregate) {
        return new ProcessingGreetingState();
    }

    @Override
    public String getStateName() { return "IDLE"; }

    @Override
    public String toString() { return "IdleGreetingState"; }
}
