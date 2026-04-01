package com.enterprise.greeting.domain.visitor;

import com.enterprise.greeting.domain.model.GreetingAggregate;
import com.enterprise.greeting.domain.model.GreetingMessage;
import com.enterprise.greeting.domain.model.GreetingSubject;

/**
 * Visitor interface for all operations performed on the greeting object graph.
 * Adding a new operation never requires modifying the domain model —
 * simply add a new {@link GreetingVisitor} implementation.
 *
 * <p>This decouples the domain structure from the operations performed on it,
 * which is absolutely essential for a two-object model like this one.</p>
 */
public interface GreetingVisitor {

    void visitAggregate(GreetingAggregate aggregate);

    void visitMessage(GreetingMessage message);

    void visitSubject(GreetingSubject subject);
}
