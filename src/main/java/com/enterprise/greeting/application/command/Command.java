package com.enterprise.greeting.application.command;

/**
 * Marker interface for all commands in the CQRS write model.
 * A command represents the intent to change state.
 * Commands are dispatched through the {@link CommandBus} and handled
 * by exactly one {@link CommandHandler}.
 *
 * <p>Every method call that was previously a direct invocation has been
 * correctly elevated to a first-class command object with full undo support.</p>
 */
public interface Command {

    /**
     * Returns the unique identifier for this command instance.
     * Used for idempotency checks, event correlation, and audit trails.
     */
    String getCommandId();
}
