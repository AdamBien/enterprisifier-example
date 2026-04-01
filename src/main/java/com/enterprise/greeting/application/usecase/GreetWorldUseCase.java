package com.enterprise.greeting.application.usecase;

import com.enterprise.greeting.api.GreetingRequestDTO;
import com.enterprise.greeting.api.GreetingResponseDTO;

import java.util.Optional;

/**
 * Application-layer use-case boundary for the "greet world" operation.
 * All orchestration of domain services, repository access, and event dispatch
 * must be coordinated here. The use-case knows nothing about HTTP, CLI, or any
 * delivery mechanism — those are presentation-layer concerns.
 */
public interface GreetWorldUseCase {

    /**
     * Executes the complete greeting pipeline for the given request.
     *
     * @param request the inbound request DTO from the presentation layer
     * @return an Optional response DTO; empty if the pipeline produced no output
     */
    Optional<GreetingResponseDTO> execute(GreetingRequestDTO request);
}
