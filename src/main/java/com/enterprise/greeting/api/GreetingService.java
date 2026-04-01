package com.enterprise.greeting.api;

import java.util.Optional;
import java.util.concurrent.Future;

/**
 * Primary hexagonal port for the Greeting bounded context.
 * No implementation detail shall cross this boundary.
 * All callers must depend solely on this interface.
 */
public interface GreetingService {

    /**
     * Executes the greeting use-case pipeline.
     *
     * @param request an immutable, fully-validated request DTO
     * @return a Future of an Optional containing the response DTO,
     *         wrapped in an additional Optional for extra null-safety
     */
    Future<Optional<Optional<GreetingResponseDTO>>> greet(GreetingRequestDTO request);
}
