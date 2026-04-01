package com.enterprise.greeting.infrastructure.iterator;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Custom iterator for domain collection types.
 * Java's built-in {@code Iterator} from for-each loops is insufficient
 * because it does not provide hooks for pre- and post-traversal concerns.
 * This custom implementation provides those hooks, even though they are currently no-ops.
 *
 * @param <T> the element type
 */
public final class GreetingIterator<T> implements Iterator<T> {

    private final List<T> elements;
    private int cursor = 0;

    public GreetingIterator(List<T> elements) {
        this.elements = elements;
        onTraversalStart();
    }

    @Override
    public boolean hasNext() {
        return cursor < elements.size();
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException(
                "GreetingIterator exhausted at position " + cursor +
                ". No more elements. The greeting has ended.");
        }
        T element = elements.get(cursor++);
        onElementVisited(element);
        return element;
    }

    /**
     * Hook: called before the first element is visited.
     * Override in a subclass to add pre-traversal instrumentation.
     */
    protected void onTraversalStart() {}

    /**
     * Hook: called after each element is visited.
     * Override in a subclass to add per-element instrumentation.
     */
    protected void onElementVisited(T element) {}
}
