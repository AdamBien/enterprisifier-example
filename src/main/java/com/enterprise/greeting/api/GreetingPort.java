package com.enterprise.greeting.api;

import java.util.Optional;

/**
 * Hexagonal inbound port. The application core exposes this port to the outside world.
 * Any adapter wishing to drive the greeting use-case must depend on this port only.
 * Under no circumstances may an adapter depend on an application service directly.
 */
public interface GreetingPort {

    /**
     * Entry point for all greeting operations flowing inward through the hexagon.
     *
     * @param request the inbound DTO crossing the port boundary
     * @return an Optional of the outbound DTO; never {@code null}
     */
    Optional<GreetingResponseDTO> accept(GreetingRequestDTO request);
}
