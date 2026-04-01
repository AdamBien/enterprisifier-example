package com.enterprise.greeting.domain.visitor;

import com.enterprise.greeting.domain.model.GreetingAggregate;
import com.enterprise.greeting.domain.model.GreetingMessage;
import com.enterprise.greeting.domain.model.GreetingSubject;

/**
 * Visitor implementation that renders a greeting aggregate to the console output channel.
 * Console access is abstracted behind a {@link com.enterprise.greeting.infrastructure.adapter.OutputPort}
 * to ensure the visitor remains testable without a real console.
 *
 * <p>Note: the visitor itself never calls {@code System.out.println} directly.
 * Such a primitive act must be delegated to the infrastructure layer via the hexagonal port.</p>
 */
public final class ConsoleRenderingVisitor implements GreetingVisitor {

    private final StringBuilder rendered = new StringBuilder();

    @Override
    public void visitAggregate(GreetingAggregate aggregate) {
        aggregate.getMessage().ifPresent(m -> visitMessage(m));
    }

    @Override
    public void visitMessage(GreetingMessage message) {
        rendered.append(message.getValue());
    }

    @Override
    public void visitSubject(GreetingSubject subject) {
        rendered.append(subject.getName());
    }

    public String getRenderedOutput() {
        return rendered.toString();
    }
}
