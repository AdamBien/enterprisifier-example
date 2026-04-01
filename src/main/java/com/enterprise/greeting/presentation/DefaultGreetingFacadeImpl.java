package com.enterprise.greeting.presentation;

import com.enterprise.greeting.api.GreetingRequestDTO;
import com.enterprise.greeting.api.GreetingResponseDTO;
import com.enterprise.greeting.api.GreetingService;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

/**
 * Default implementation of the {@link GreetingFacade}.
 * Builds the {@link GreetingRequestDTO} and delegates to the {@link GreetingService}.
 * No business logic may appear in this class. It is exclusively a translation and delegation layer.
 */
public final class DefaultGreetingFacadeImpl implements GreetingFacade {

    private static final Logger LOG = Logger.getLogger(DefaultGreetingFacadeImpl.class.getName());

    private final GreetingService greetingService;

    public DefaultGreetingFacadeImpl(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @Override
    public Optional<GreetingResponseDTO> greetDefault() {
        return greetSubject("world");
    }

    @Override
    public Optional<GreetingResponseDTO> greetSubject(String subjectName) {
        GreetingRequestDTO request = GreetingRequestDTO.builder()
            .subjectName(subjectName)
            .locale("en")
            .correlationId(UUID.randomUUID().toString())
            .build();

        try {
            return greetingService.greet(request)
                .get()
                .flatMap(opt -> opt);
        } catch (InterruptedException | ExecutionException ex) {
            LOG.severe("[Facade] Greeting pipeline interrupted: " + ex.getMessage());
            Thread.currentThread().interrupt();
            return Optional.empty();
        }
    }
}
