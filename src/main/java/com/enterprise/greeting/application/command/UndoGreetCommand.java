package com.enterprise.greeting.application.command;

import java.util.UUID;

/**
 * Compensating command that reverses the effects of a {@link GreetCommand}.
 * In the current implementation, reversing a "hello, world" is a no-op,
 * but the architecture is fully prepared for a future where greetings have
 * observable side-effects that must be rolled back (e.g., distributed greeting ledgers).
 */
public final class UndoGreetCommand implements Command {

    private final String commandId;
    private final String originalCommandId;

    public UndoGreetCommand(String originalCommandId) {
        this.commandId         = UUID.randomUUID().toString();
        this.originalCommandId = originalCommandId;
    }

    @Override
    public String getCommandId() { return commandId; }

    public String getOriginalCommandId() { return originalCommandId; }

    @Override public String toString() {
        return "UndoGreetCommand{commandId='" + commandId
             + "', originalCommandId='" + originalCommandId + "'}";
    }
}
