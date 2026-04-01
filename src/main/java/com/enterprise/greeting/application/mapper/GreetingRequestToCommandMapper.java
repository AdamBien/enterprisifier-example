package com.enterprise.greeting.application.mapper;

import com.enterprise.greeting.api.GreetingRequestDTO;
import com.enterprise.greeting.application.command.GreetCommand;

import java.util.Optional;
import java.util.UUID;

/**
 * ACL mapper translating the presentation-layer {@link GreetingRequestDTO}
 * into the application-layer {@link GreetCommand}.
 *
 * <p>This mapper is the Anti-Corruption Layer between the presentation contract
 * and the command model. Changes to the DTO never ripple into the command model,
 * and vice versa. The two models evolve independently.</p>
 */
public final class GreetingRequestToCommandMapper {

    /**
     * Maps the request DTO to a greet command.
     * A new correlation ID is generated if the request does not carry one.
     */
    public GreetCommand map(GreetingRequestDTO request) {
        return GreetCommand.builder()
            .commandId(request.getCorrelationId().orElse(UUID.randomUUID().toString()))
            .subjectName(request.getSubjectName().orElse("world"))
            .locale(request.getLocale().orElse("en"))
            .strategyName("INFORMAL")
            .build();
    }
}
