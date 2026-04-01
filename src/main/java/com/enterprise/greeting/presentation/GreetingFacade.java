package com.enterprise.greeting.presentation;

import com.enterprise.greeting.api.GreetingResponseDTO;

import java.util.Optional;

/**
 * Presentation-layer facade. The single entry-point for all inbound greeting requests.
 * External callers (CLI, REST controllers, message consumers) interact only with this facade.
 * They must never reference the application layer, domain layer, or infrastructure layer.
 *
 * <p>The facade hides the complexity of 73 collaborating classes behind a single method.
 * This is the primary value proposition of the entire framework.</p>
 */
public interface GreetingFacade {

    /**
     * Initiates a greeting for the default subject using default configuration.
     *
     * @return an Optional of the response DTO; empty if the greeting pipeline produced no output
     */
    Optional<GreetingResponseDTO> greetDefault();

    /**
     * Initiates a greeting for the specified subject.
     *
     * @param subjectName the name of the entity to greet
     * @return an Optional of the response DTO
     */
    Optional<GreetingResponseDTO> greetSubject(String subjectName);
}
