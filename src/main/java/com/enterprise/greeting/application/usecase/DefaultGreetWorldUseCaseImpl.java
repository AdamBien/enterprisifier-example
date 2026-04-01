package com.enterprise.greeting.application.usecase;

import com.enterprise.greeting.api.GreetingRequestDTO;
import com.enterprise.greeting.api.GreetingResponseDTO;
import com.enterprise.greeting.application.command.GreetCommand;
import com.enterprise.greeting.application.command.CommandBus;
import com.enterprise.greeting.application.mapper.GreetingRequestToCommandMapper;
import com.enterprise.greeting.application.mapper.GreetingResultToResponseMapper;
import com.enterprise.greeting.domain.events.DomainEventBus;
import com.enterprise.greeting.domain.events.GreetingRequestedEvent;
import com.enterprise.greeting.domain.model.GreetingAggregate;

import java.util.Optional;
import java.util.logging.Logger;

/**
 * Default implementation of {@link GreetWorldUseCase}.
 * This class must never be instantiated directly. Use the
 * {@link com.enterprise.greeting.infrastructure.factory.GreetingServiceFactory}.
 *
 * <p>Orchestration flow:
 * <ol>
 *   <li>Map inbound DTO → command via the mapper ACL</li>
 *   <li>Publish domain event: GreetingRequested</li>
 *   <li>Dispatch command through the command bus</li>
 *   <li>Map aggregate result → outbound DTO via the mapper ACL</li>
 * </ol>
 * </p>
 */
public final class DefaultGreetWorldUseCaseImpl implements GreetWorldUseCase {

    private static final Logger LOG = Logger.getLogger(DefaultGreetWorldUseCaseImpl.class.getName());

    private final CommandBus commandBus;
    private final DomainEventBus eventBus;
    private final GreetingRequestToCommandMapper requestMapper;
    private final GreetingResultToResponseMapper resultMapper;

    public DefaultGreetWorldUseCaseImpl(
            CommandBus commandBus,
            DomainEventBus eventBus,
            GreetingRequestToCommandMapper requestMapper,
            GreetingResultToResponseMapper resultMapper) {
        this.commandBus    = commandBus;
        this.eventBus      = eventBus;
        this.requestMapper = requestMapper;
        this.resultMapper  = resultMapper;
    }

    @Override
    public Optional<GreetingResponseDTO> execute(GreetingRequestDTO request) {
        LOG.info("[UseCase] Executing GreetWorldUseCase");

        GreetCommand command = requestMapper.map(request);

        eventBus.publish(new GreetingRequestedEvent(
            command.getCommandId(),
            request.getSubjectName().orElse("world")
        ));

        Optional<GreetingAggregate> result = commandBus.dispatch(command);

        return result.flatMap(resultMapper::map);
    }
}
