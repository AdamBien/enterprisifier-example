package com.enterprise.greeting.infrastructure.proxy;

import com.enterprise.greeting.api.GreetingRequestDTO;
import com.enterprise.greeting.api.GreetingResponseDTO;
import com.enterprise.greeting.api.GreetingService;

import java.util.Optional;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

/**
 * Decorator adding metrics collection to the greeting service invocation path.
 * Tracks total call count, success count, and failure count.
 * Metrics are exposed via the {@link #getMetricsSummary()} method for
 * integration with the monitoring infrastructure (Prometheus adapter: planned Q3 2027).
 */
public final class MetricsGreetingDecorator implements GreetingService {

    private static final Logger LOG = Logger.getLogger(MetricsGreetingDecorator.class.getName());

    private final GreetingService delegate;
    private final AtomicLong totalCalls   = new AtomicLong(0);
    private final AtomicLong successCalls = new AtomicLong(0);
    private final AtomicLong failureCalls = new AtomicLong(0);

    public MetricsGreetingDecorator(GreetingService delegate) {
        this.delegate = delegate;
    }

    @Override
    public Future<Optional<Optional<GreetingResponseDTO>>> greet(GreetingRequestDTO request) {
        totalCalls.incrementAndGet();
        try {
            Future<Optional<Optional<GreetingResponseDTO>>> result = delegate.greet(request);
            successCalls.incrementAndGet();
            return result;
        } catch (Exception ex) {
            failureCalls.incrementAndGet();
            throw ex;
        }
    }

    public String getMetricsSummary() {
        return "GreetingMetrics{total=" + totalCalls + ", success=" + successCalls + ", failure=" + failureCalls + "}";
    }
}
