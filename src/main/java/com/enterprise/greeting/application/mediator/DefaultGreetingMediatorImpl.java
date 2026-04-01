package com.enterprise.greeting.application.mediator;

import com.enterprise.greeting.api.GreetingRequestDTO;
import com.enterprise.greeting.api.GreetingResponseDTO;
import com.enterprise.greeting.application.saga.GreetingOrchestrationSaga;
import com.enterprise.greeting.application.usecase.GreetWorldUseCase;

import java.util.Optional;
import java.util.logging.Logger;

/**
 * Default implementation of {@link GreetingMediator}.
 * Delegates to the {@link GreetingOrchestrationSaga} for the full orchestration pipeline,
 * and to the {@link GreetWorldUseCase} for direct use-case execution.
 *
 * <p>The mediator chooses the appropriate delegation strategy based on
 * context flags that may be added in a future sprint.</p>
 */
public final class DefaultGreetingMediatorImpl implements GreetingMediator {

    private static final Logger LOG = Logger.getLogger(DefaultGreetingMediatorImpl.class.getName());

    private final GreetWorldUseCase useCase;
    private final GreetingOrchestrationSaga saga;

    public DefaultGreetingMediatorImpl(GreetWorldUseCase useCase, GreetingOrchestrationSaga saga) {
        this.useCase = useCase;
        this.saga    = saga;
    }

    @Override
    public Optional<GreetingResponseDTO> mediate(GreetingRequestDTO request) {
        LOG.info("[Mediator] Mediating greeting request: " + request);
        return saga.execute(request);
    }
}
