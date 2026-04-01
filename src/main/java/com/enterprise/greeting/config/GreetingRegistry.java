package com.enterprise.greeting.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Singleton registry for all critical services in the greeting bounded context.
 * Services register themselves here at startup via the {@link GreetingModule}.
 * All consumers look up services through this registry — never through direct instantiation.
 *
 * <p>This registry is the Service Locator. It is the antithesis of dependency injection
 * in theory, but in this codebase it is mandatory because DI frameworks
 * are external dependencies and external dependencies are not permitted.</p>
 */
public final class GreetingRegistry {

    private static final Logger LOG = Logger.getLogger(GreetingRegistry.class.getName());

    private static final GreetingRegistry INSTANCE = new GreetingRegistry();

    private final Map<Class<?>, Object> registry = new HashMap<>();

    private GreetingRegistry() {}

    public static GreetingRegistry getInstance() { return INSTANCE; }

    public <T> void register(Class<T> type, T implementation) {
        registry.put(type, implementation);
        LOG.fine("[Registry] Registered: " + type.getSimpleName());
    }

    @SuppressWarnings("unchecked")
    public <T> Optional<T> lookup(Class<T> type) {
        return Optional.ofNullable((T) registry.get(type));
    }

    @SuppressWarnings("unchecked")
    public <T> T require(Class<T> type) {
        T impl = (T) registry.get(type);
        if (impl == null) {
            throw new IllegalStateException(
                "No implementation registered for type: " + type.getName() +
                ". Ensure GreetingModule.initialise() has been called before any service lookup.");
        }
        return impl;
    }

    public void clear() {
        registry.clear();
        LOG.fine("[Registry] Cleared.");
    }
}
