package com.enterprise.greeting.application.handler;

import com.enterprise.greeting.application.command.GreetCommand;

import java.util.Optional;

/**
 * Chain of Responsibility link for pre-processing {@link GreetCommand} objects.
 * Each handler performs a single concern (validation, enrichment, audit, execution)
 * and delegates to the next handler in the chain.
 *
 * <p>The chain is assembled in the {@link com.enterprise.greeting.config.GreetingModule}.
 * Adding a new cross-cutting concern never requires modifying an existing handler.</p>
 */
public interface GreetingHandler {

    /**
     * Processes the command and optionally passes it to the next handler.
     *
     * @param command the command to process
     * @return a processed command wrapped in Optional; empty aborts the chain
     */
    Optional<GreetCommand> handle(GreetCommand command);

    /**
     * Sets the next handler in the chain.
     *
     * @param next the subsequent handler
     */
    void setNext(GreetingHandler next);
}
