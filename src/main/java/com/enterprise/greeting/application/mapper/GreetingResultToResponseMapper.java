package com.enterprise.greeting.application.mapper;

import com.enterprise.greeting.api.GreetingResponseDTO;
import com.enterprise.greeting.domain.model.GreetingAggregate;

import java.time.Instant;
import java.util.Optional;

/**
 * ACL mapper translating the domain {@link GreetingAggregate} result
 * into the presentation-layer {@link GreetingResponseDTO}.
 *
 * <p>Domain objects must never escape the application layer boundary.
 * This mapper is the Anti-Corruption Layer on the outbound path,
 * ensuring the presentation layer never takes a compile-time dependency
 * on a domain class.</p>
 */
public final class GreetingResultToResponseMapper {

    public Optional<GreetingResponseDTO> map(GreetingAggregate aggregate) {
        return aggregate.getMessage()
            .map(msg -> GreetingResponseDTO.builder()
                .renderedGreeting(msg.getValue())
                .correlationId(aggregate.getAggregateId())
                .processedAt(Instant.now())
                .strategyUsed(aggregate.getCurrentState().getStateName())
                .build());
    }
}
