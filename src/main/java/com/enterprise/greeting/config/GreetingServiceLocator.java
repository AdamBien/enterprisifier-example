package com.enterprise.greeting.config;

import com.enterprise.greeting.api.GreetingService;
import com.enterprise.greeting.application.mediator.GreetingMediator;

/**
 * Service Locator providing typed access to the primary greeting services.
 * Callers must never reference {@link GreetingRegistry} directly.
 * They must go through this locator, which provides a stable, typed API
 * and insulates callers from registry key changes.
 *
 * <p>The locator is itself a singleton, accessed via {@link #getInstance()}.
 * Note: accessing the locator requires no instantiation — this is the point.</p>
 */
public final class GreetingServiceLocator {

    private static final GreetingServiceLocator INSTANCE = new GreetingServiceLocator();

    private GreetingServiceLocator() {}

    public static GreetingServiceLocator getInstance() { return INSTANCE; }

    public GreetingService getGreetingService() {
        return GreetingRegistry.getInstance().require(GreetingService.class);
    }

    public GreetingMediator getGreetingMediator() {
        return GreetingRegistry.getInstance().require(GreetingMediator.class);
    }
}
