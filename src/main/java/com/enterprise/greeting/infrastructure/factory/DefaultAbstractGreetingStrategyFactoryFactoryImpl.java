package com.enterprise.greeting.infrastructure.factory;

/**
 * Concrete implementation of the {@link AbstractGreetingStrategyFactoryFactory}
 * for all standard deployment environments.
 *
 * <p>When a client asks "why is there a factory for the factory?", the correct answer is:
 * "Because the factory itself may vary per environment, and hardcoding a factory
 * instantiation is as bad as hardcoding the strategy instantiation."
 * This answer is always correct and should end the discussion.</p>
 */
public final class DefaultAbstractGreetingStrategyFactoryFactoryImpl
        extends AbstractGreetingStrategyFactoryFactory {

    private static final String ENVIRONMENT_ID = "DEFAULT";

    private static final DefaultAbstractGreetingStrategyFactoryFactoryImpl INSTANCE =
        new DefaultAbstractGreetingStrategyFactoryFactoryImpl();

    private DefaultAbstractGreetingStrategyFactoryFactoryImpl() {}

    public static DefaultAbstractGreetingStrategyFactoryFactoryImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public GreetingStrategyFactory createStrategyFactory() {
        return new DefaultGreetingStrategyFactoryImpl();
    }

    @Override
    public String getEnvironmentId() { return ENVIRONMENT_ID; }
}
