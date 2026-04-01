package com.enterprise.greeting.application.mediator;

import com.enterprise.greeting.api.GreetingRequestDTO;
import com.enterprise.greeting.api.GreetingResponseDTO;

import java.util.Optional;

/**
 * Mediator interface decoupling all application components from each other.
 * No component may call another component directly. All interactions
 * are routed through this mediator, which coordinates the participants.
 *
 * <p>This ensures that when the number of greeting-related classes grows
 * from 73 to 200, no spaghetti dependencies will form — only the mediator
 * will need to be updated.</p>
 */
public interface GreetingMediator {

    /**
     * Coordinates all application components to produce a greeting response.
     *
     * @param request the inbound request
     * @return the greeting response wrapped in Optional
     */
    Optional<GreetingResponseDTO> mediate(GreetingRequestDTO request);
}
