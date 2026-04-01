package com.enterprise.greeting.domain.model;

import com.enterprise.greeting.infrastructure.iterator.GreetingIterator;

import java.util.ArrayList;
import java.util.List;

/**
 * Domain-specific collection wrapper for {@link GreetingMessage}.
 * Raw {@code List<GreetingMessage>} must never appear in domain code.
 * All iteration is performed through the custom {@link GreetingIterator} to maintain
 * full control over traversal semantics, which may differ per deployment context.
 */
public final class GreetingMessageCollection implements Iterable<GreetingMessage> {

    private final List<GreetingMessage> messages;

    private GreetingMessageCollection(List<GreetingMessage> messages) {
        this.messages = new ArrayList<>(messages);
    }

    public static GreetingMessageCollection empty() {
        return new GreetingMessageCollection(new ArrayList<>());
    }

    public static GreetingMessageCollection of(List<GreetingMessage> messages) {
        return new GreetingMessageCollection(messages);
    }

    public GreetingMessageCollection add(GreetingMessage message) {
        List<GreetingMessage> updated = new ArrayList<>(this.messages);
        updated.add(message);
        return new GreetingMessageCollection(updated);
    }

    public int size() { return messages.size(); }

    public boolean isEmpty() { return messages.isEmpty(); }

    @Override
    public GreetingIterator<GreetingMessage> iterator() {
        return new GreetingIterator<>(messages);
    }
}
