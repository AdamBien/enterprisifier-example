package com.enterprise.greeting.application.command;

import com.enterprise.greeting.domain.model.GreetingAggregate;
import com.enterprise.greeting.domain.model.GreetingMessage;
import com.enterprise.greeting.domain.model.GreetingSubject;
import com.enterprise.greeting.domain.strategy.GreetingStrategy;
import com.enterprise.greeting.infrastructure.factory.GreetingStrategyFactory;

import java.util.Optional;
import java.util.logging.Logger;

/**
 * Handler for {@link GreetCommand}. Builds a {@link GreetingAggregate},
 * selects the appropriate {@link GreetingStrategy} via the factory,
 * applies the strategy, and returns the rendered aggregate.
 *
 * <p>This handler contains zero business logic. Business logic lives exclusively
 * in the domain layer. This handler is purely an orchestration glue class.</p>
 */
public final class GreetCommandHandler implements CommandHandler<GreetCommand> {

    private static final Logger LOG = Logger.getLogger(GreetCommandHandler.class.getName());

    private final GreetingStrategyFactory strategyFactory;

    public GreetCommandHandler(GreetingStrategyFactory strategyFactory) {
        this.strategyFactory = strategyFactory;
    }

    @Override
    public Optional<GreetingAggregate> handle(GreetCommand command) {
        LOG.info("[Handler] Handling GreetCommand: " + command.getCommandId());

        String subjectName   = command.getSubjectName().orElse("world");
        String strategyName  = command.getStrategyName().orElse("INFORMAL");

        GreetingSubject  subject  = GreetingSubject.of(subjectName);
        GreetingStrategy strategy = strategyFactory.create(strategyName);

        Optional<GreetingMessage> message = strategy.compose(subject);

        GreetingAggregate aggregate = GreetingAggregate.builder()
            .aggregateId(command.getCommandId())
            .subject(subject)
            .message(message.orElse(null))
            .build();

        aggregate.renderGreeting();

        return Optional.of(aggregate);
    }

    @Override
    public Class<GreetCommand> getCommandType() { return GreetCommand.class; }
}
