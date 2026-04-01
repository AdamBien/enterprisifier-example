package com.enterprise.greeting.application.command;

import com.enterprise.greeting.domain.model.GreetingAggregate;

import java.util.Optional;

/**
 * Generic command handler contract.
 * Each command type maps to exactly one handler.
 * Handlers are registered in the {@link CommandBus} at startup via the
 * {@link com.enterprise.greeting.config.GreetingModule}.
 *
 * @param <C> the command type this handler processes
 */
public interface CommandHandler<C extends Command> {

    /**
     * Handles the given command and returns the resulting aggregate state.
     *
     * @param command the command to handle
     * @return an Optional containing the updated aggregate; empty on no-op
     */
    Optional<GreetingAggregate> handle(C command);

    /**
     * Returns the command type this handler is registered to process.
     */
    Class<C> getCommandType();
}
