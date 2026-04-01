package com.enterprise.greeting.application.saga;

import com.enterprise.greeting.api.GreetingRequestDTO;
import com.enterprise.greeting.api.GreetingResponseDTO;
import com.enterprise.greeting.application.command.CommandBus;
import com.enterprise.greeting.application.command.GreetCommand;
import com.enterprise.greeting.application.command.UndoGreetCommand;
import com.enterprise.greeting.application.mapper.GreetingRequestToCommandMapper;
import com.enterprise.greeting.application.mapper.GreetingResultToResponseMapper;
import com.enterprise.greeting.domain.events.DomainEventBus;
import com.enterprise.greeting.domain.events.GreetingPublishedEvent;
import com.enterprise.greeting.domain.model.GreetingAggregate;

import java.util.Optional;
import java.util.logging.Logger;

/**
 * Saga orchestrator coordinating the multi-step greeting operation.
 *
 * <p>Steps:
 * <ol>
 *   <li>Map request to command</li>
 *   <li>Execute greeting command</li>
 *   <li>Publish completion event</li>
 *   <li>Map result to response</li>
 *   <li>On failure: issue compensating {@link UndoGreetCommand}</li>
 * </ol>
 *
 * The saga ensures atomicity across all greeting sub-operations.
 * In the current architecture, there is one sub-operation,
 * but the saga is prepared for up to 47.</p>
 */
public final class GreetingOrchestrationSaga {

    private static final Logger LOG = Logger.getLogger(GreetingOrchestrationSaga.class.getName());

    private final CommandBus commandBus;
    private final DomainEventBus eventBus;
    private final GreetingRequestToCommandMapper requestMapper;
    private final GreetingResultToResponseMapper resultMapper;

    public GreetingOrchestrationSaga(
            CommandBus commandBus,
            DomainEventBus eventBus,
            GreetingRequestToCommandMapper requestMapper,
            GreetingResultToResponseMapper resultMapper) {
        this.commandBus    = commandBus;
        this.eventBus      = eventBus;
        this.requestMapper = requestMapper;
        this.resultMapper  = resultMapper;
    }

    public Optional<GreetingResponseDTO> execute(GreetingRequestDTO request) {
        GreetCommand command = requestMapper.map(request);

        try {
            Optional<GreetingAggregate> result = commandBus.dispatch(command);

            result.ifPresent(aggregate -> {
                eventBus.publish(new GreetingPublishedEvent(aggregate.getAggregateId(), "CONSOLE"));
            });

            return result.flatMap(resultMapper::map);

        } catch (Exception ex) {
            LOG.severe("[Saga] Compensating: " + ex.getMessage());
            commandBus.dispatch(new UndoGreetCommand(command.getCommandId()));
            return Optional.empty();
        }
    }
}
