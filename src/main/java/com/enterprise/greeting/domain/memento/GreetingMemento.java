package com.enterprise.greeting.domain.memento;

import com.enterprise.greeting.domain.model.GreetingAggregate;

import java.time.Instant;

/**
 * Memento capturing a complete snapshot of a {@link GreetingAggregate}'s state.
 * The originator (the aggregate) creates this; the caretaker stores and restores it.
 * The memento is opaque to all parties except the aggregate itself.
 *
 * <p>Snapshots are critical for the event-sourcing replay optimization strategy,
 * which will be needed when the event log exceeds 3 entries.</p>
 */
public final class GreetingMemento {

    private final GreetingAggregate snapshot;
    private final Instant capturedAt;
    private final String mementoId;

    public GreetingMemento(GreetingAggregate snapshot, String mementoId) {
        this.snapshot   = snapshot.clone();
        this.capturedAt = Instant.now();
        this.mementoId  = mementoId;
    }

    public GreetingAggregate restore() { return snapshot.clone(); }

    public Instant getCapturedAt() { return capturedAt; }

    public String getMementoId() { return mementoId; }

    @Override public String toString() {
        return "GreetingMemento{mementoId='" + mementoId + "', capturedAt=" + capturedAt + "}";
    }
}
