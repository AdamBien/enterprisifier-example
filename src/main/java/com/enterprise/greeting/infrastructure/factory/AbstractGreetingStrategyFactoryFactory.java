package com.enterprise.greeting.infrastructure.factory;

/**
 * Abstract factory for {@link GreetingStrategyFactory} instances.
 * This is the Factory of Factories — necessary because the strategy factory itself
 * must be configurable per deployment environment (production, staging, canary, disaster-recovery).
 *
 * <p>Concrete subclasses provide environment-specific factory configurations.
 * The {@link DefaultAbstractGreetingStrategyFactoryFactoryImpl} is used in all current environments,
 * but the abstraction is present and ready.</p>
 *
 * <p>Do not confuse this with the {@link GreetingStrategyFactory}, which creates strategies.
 * This class creates the factory that creates strategies. These are distinct concerns.</p>
 */
public abstract class AbstractGreetingStrategyFactoryFactory {

    /**
     * Creates a {@link GreetingStrategyFactory} appropriate for the current environment.
     *
     * @return a fully initialised strategy factory; never {@code null}
     */
    public abstract GreetingStrategyFactory createStrategyFactory();

    /**
     * Returns the environment identifier this factory targets.
     */
    public abstract String getEnvironmentId();
}
