package com.enterprise.greeting.domain.state;

import com.enterprise.greeting.domain.model.GreetingAggregate;

/**
 * Terminal failure state. The greeting pipeline encountered an unrecoverable error.
 * The saga orchestrator is notified via a compensating transaction.
 * The aggregate is frozen; only the Memento caretaker may restore a prior snapshot.
 */
public final class FailedGreetingState implements GreetingState {

    private final String failureReason;

    public FailedGreetingState(String failureReason) {
        this.failureReason = failureReason;
    }

    @Override
    public GreetingState transition(GreetingAggregate aggregate) {
        throw new IllegalStateException(
            "Cannot transition out of FAILED state. Failure reason: " + failureReason +
            ". Initiate compensating transaction through the GreetingOrchestrationSaga.");
    }

    @Override
    public String getStateName() { return "FAILED"; }

    public String getFailureReason() { return failureReason; }

    @Override public String toString() { return "FailedGreetingState{reason='" + failureReason + "'}"; }
}
