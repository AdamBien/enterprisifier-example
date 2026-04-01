package com.enterprise.greeting.domain.state;

import com.enterprise.greeting.domain.model.GreetingAggregate;

/**
 * State machine contract for the Greeting lifecycle.
 * The greeting process traverses a well-defined state graph:
 *
 * <pre>
 *   IDLE ──▶ PROCESSING ──▶ COMPLETED
 *              │
 *              ▼
 *            FAILED
 * </pre>
 *
 * Each state encapsulates its own transition logic, eliminating all {@code if/switch}
 * statements from the aggregate. The aggregate never checks its own state directly.
 */
public interface GreetingState {

    /**
     * Performs the transition from this state to the appropriate next state.
     *
     * @param aggregate the aggregate root driving the state machine
     * @return the next state; must never return {@code null}
     */
    GreetingState transition(GreetingAggregate aggregate);

    /**
     * Returns the canonical name of this state for logging and event sourcing.
     */
    String getStateName();
}
