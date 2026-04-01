package com.enterprise.greeting.application.command;

import com.enterprise.greeting.domain.model.GreetingAggregate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Default synchronous implementation of the {@link CommandBus}.
 * Maintains a type-keyed handler registry. Dispatches are O(1) via hash lookup.
 *
 * <p>This class is instantiated exclusively by the
 * {@link com.enterprise.greeting.config.GreetingModule}.</p>
 */
@SuppressWarnings("unchecked")
public final class DefaultCommandBusImpl implements CommandBus {

    private static final Logger LOG = Logger.getLogger(DefaultCommandBusImpl.class.getName());

    private final Map<Class<?>, CommandHandler<?>> handlers = new HashMap<>();

    DefaultCommandBusImpl() {}

    @Override
    public Optional<GreetingAggregate> dispatch(Command command) {
        CommandHandler<Command> handler = (CommandHandler<Command>) handlers.get(command.getClass());
        if (handler == null) {
            throw new IllegalStateException(
                "No handler registered for command type: " + command.getClass().getName() +
                ". Register a CommandHandler via CommandBus#register() in the GreetingModule.");
        }
        LOG.info("[CommandBus] Dispatching: " + command);
        return handler.handle(command);
    }

    @Override
    public <C extends Command> void register(CommandHandler<C> handler) {
        handlers.put(handler.getCommandType(), handler);
        LOG.fine("[CommandBus] Handler registered for: " + handler.getCommandType().getSimpleName());
    }
}
