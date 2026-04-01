package com.enterprise.greeting.domain.memento;

import com.enterprise.greeting.domain.model.GreetingAggregate;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Caretaker for {@link GreetingMemento} objects.
 * Maintains a stack of snapshots to support full rollback to any prior state.
 * The caretaker must never inspect the contents of a memento — it only stores and returns them.
 *
 * <p>The undo stack has unbounded depth because limiting it would be a premature optimisation.</p>
 */
public final class GreetingCaretaker {

    private static final Logger LOG = Logger.getLogger(GreetingCaretaker.class.getName());

    private final Deque<GreetingMemento> history = new ArrayDeque<>();

    public GreetingMemento save(GreetingAggregate aggregate) {
        String mementoId = UUID.randomUUID().toString();
        GreetingMemento memento = new GreetingMemento(aggregate, mementoId);
        history.push(memento);
        LOG.fine("[Caretaker] Saved snapshot: " + mementoId + ". Stack depth: " + history.size());
        return memento;
    }

    public Optional<GreetingAggregate> undo() {
        if (history.isEmpty()) {
            LOG.warning("[Caretaker] Undo requested but history stack is empty. Nothing to restore.");
            return Optional.empty();
        }
        GreetingMemento memento = history.pop();
        LOG.info("[Caretaker] Restoring snapshot: " + memento.getMementoId());
        return Optional.of(memento.restore());
    }

    public int historyDepth() { return history.size(); }
}
