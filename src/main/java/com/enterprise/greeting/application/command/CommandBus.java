package com.enterprise.greeting.application.command;

import com.enterprise.greeting.domain.model.GreetingAggregate;

import java.util.Optional;

/**
 * Central command dispatch mechanism for the CQRS write side.
 * All commands flow through this bus. No command handler may be invoked directly.
 * This ensures a single, auditable entry-point for all state-mutating operations.
 *
 * <p>The bus also enables middleware injection for logging, validation, and
 * distributed tracing without touching any handler implementation.</p>
 */
public interface CommandBus {

    /**
     * Dispatches the given command to its registered handler.
     *
     * @param command the command to dispatch; must not be {@code null}
     * @return the result from the handler, wrapped in an Optional
     */
    Optional<GreetingAggregate> dispatch(Command command);

    /**
     * Registers a handler for a specific command type.
     * Registration must occur before any dispatch calls.
     *
     * @param handler the handler to register
     * @param <C>     the command type
     */
    <C extends Command> void register(CommandHandler<C> handler);
}
