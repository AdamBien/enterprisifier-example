package com.enterprise.greeting.infrastructure.factory;

import com.enterprise.greeting.api.GreetingService;

/**
 * Factory for {@link GreetingService} instances.
 * All service objects are created here; the {@code new} keyword is never
 * used outside of factory classes.
 *
 * <p>This factory is accessed via the {@link com.enterprise.greeting.config.GreetingServiceLocator}.
 * Direct instantiation of this factory is also forbidden; use the service locator.</p>
 */
public interface GreetingServiceFactory {

    /**
     * Creates a fully wired {@link GreetingService} with all dependencies satisfied.
     *
     * @return the constructed service; never {@code null}
     */
    GreetingService createGreetingService();
}
