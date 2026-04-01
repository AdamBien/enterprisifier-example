package com.enterprise.greeting.config;

import com.enterprise.greeting.api.GreetingRequestDTO;
import com.enterprise.greeting.api.GreetingResponseDTO;
import com.enterprise.greeting.api.GreetingService;
import com.enterprise.greeting.application.mediator.GreetingMediator;
import com.enterprise.greeting.infrastructure.adapter.OutputPort;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * Primary implementation of the {@link GreetingService} port.
 * Coordinates the {@link GreetingMediator} for pipeline execution
 * and the {@link OutputPort} for emitting the rendered greeting.
 *
 * <p>Instantiated exclusively by {@link GreetingModule}.</p>
 */
final class DefaultGreetingServiceImpl implements GreetingService {

    private final GreetingMediator mediator;
    private final OutputPort outputPort;

    DefaultGreetingServiceImpl(GreetingMediator mediator, OutputPort outputPort) {
        this.mediator   = mediator;
        this.outputPort = outputPort;
    }

    @Override
    public Future<Optional<Optional<GreetingResponseDTO>>> greet(GreetingRequestDTO request) {
        Optional<GreetingResponseDTO> response = mediator.mediate(request);

        response.flatMap(GreetingResponseDTO::getRenderedGreeting)
                .ifPresent(outputPort::emitLine);

        return CompletableFuture.completedFuture(
            Optional.of(response)
        );
    }
}
