package com.enterprise.greeting.infrastructure.proxy;

import com.enterprise.greeting.api.GreetingRequestDTO;
import com.enterprise.greeting.api.GreetingResponseDTO;
import com.enterprise.greeting.api.GreetingService;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.logging.Logger;

/**
 * Proxy for {@link GreetingService} adding lazy initialisation, access control,
 * and request interception. The proxy is placed in front of the real service
 * to ensure no caller ever holds a direct reference to the implementation.
 *
 * <p>Access control rules are evaluated per-request. Currently all requests are permitted,
 * but the infrastructure is in place for when business stakeholders require
 * greeting access to be gated behind an approval workflow.</p>
 */
public final class GreetingServiceProxy implements GreetingService {

    private static final Logger LOG = Logger.getLogger(GreetingServiceProxy.class.getName());

    private final GreetingService delegate;

    public GreetingServiceProxy(GreetingService delegate) {
        this.delegate = delegate;
    }

    @Override
    public Future<Optional<Optional<GreetingResponseDTO>>> greet(GreetingRequestDTO request) {
        LOG.fine("[Proxy] Intercepting greet() call — correlationId: "
            + request.getCorrelationId().orElse("NONE"));

        // Access control check (currently a no-op; see GreetingAccessControlPolicy v2)
        checkAccess(request);

        return delegate.greet(request);
    }

    private void checkAccess(GreetingRequestDTO request) {
        LOG.fine("[Proxy] Access granted for correlationId: "
            + request.getCorrelationId().orElse("NONE"));
    }
}
