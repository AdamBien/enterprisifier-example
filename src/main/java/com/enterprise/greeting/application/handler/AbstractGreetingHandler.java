package com.enterprise.greeting.application.handler;

import com.enterprise.greeting.application.command.GreetCommand;

import java.util.Optional;

/**
 * Abstract base class implementing the Template Method pattern for chain handlers.
 * Subclasses override {@link #process(GreetCommand)} for their specific concern.
 * The chain-delegation logic is provided here so subclasses never forget to call next.
 *
 * <p>This class is where the Template Method and Chain of Responsibility patterns
 * intersect, producing a synergy that is greater than the sum of its patterns.</p>
 */
public abstract class AbstractGreetingHandler implements GreetingHandler {

    private GreetingHandler next;

    @Override
    public final Optional<GreetCommand> handle(GreetCommand command) {
        Optional<GreetCommand> result = process(command);
        if (result.isPresent() && next != null) {
            return next.handle(result.get());
        }
        return result;
    }

    @Override
    public final void setNext(GreetingHandler next) {
        this.next = next;
    }

    /**
     * Template method hook: perform this handler's single responsibility.
     *
     * @param command the incoming command
     * @return the (possibly modified) command, or empty to abort the chain
     */
    protected abstract Optional<GreetCommand> process(GreetCommand command);
}
