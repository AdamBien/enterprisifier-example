package com.enterprise.greeting.infrastructure.proxy;

import com.enterprise.greeting.api.GreetingRequestDTO;
import com.enterprise.greeting.api.GreetingResponseDTO;
import com.enterprise.greeting.api.GreetingService;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.logging.Logger;

/**
 * Decorator adding structured logging around every {@link GreetingService} invocation.
 * Logs entry, exit, duration, and correlation ID at appropriate log levels.
 * Logging is a cross-cutting concern and must never appear inline in business logic.
 *
 * <p>Wraps the {@link GreetingServiceProxy}, which wraps the real service,
 * forming a decoration chain that is entirely transparent to callers.</p>
 */
public final class LoggingGreetingDecorator implements GreetingService {

    private static final Logger LOG = Logger.getLogger(LoggingGreetingDecorator.class.getName());

    private final GreetingService delegate;

    public LoggingGreetingDecorator(GreetingService delegate) {
        this.delegate = delegate;
    }

    @Override
    public Future<Optional<Optional<GreetingResponseDTO>>> greet(GreetingRequestDTO request) {
        Instant start = Instant.now();
        String correlationId = request.getCorrelationId().orElse("NONE");

        LOG.info("[Logging] ENTER greet() — correlationId=" + correlationId);

        try {
            Future<Optional<Optional<GreetingResponseDTO>>> result = delegate.greet(request);
            long ms = Duration.between(start, Instant.now()).toMillis();
            LOG.info("[Logging] EXIT greet() — correlationId=" + correlationId + " — duration=" + ms + "ms");
            return result;
        } catch (Exception ex) {
            LOG.severe("[Logging] ERROR in greet() — correlationId=" + correlationId + " — " + ex.getMessage());
            throw ex;
        }
    }
}
