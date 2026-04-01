package com.enterprise.greeting.infrastructure.factory;

import com.enterprise.greeting.domain.strategy.FormalGreetingStrategy;
import com.enterprise.greeting.domain.strategy.GreetingStrategy;
import com.enterprise.greeting.domain.strategy.InformalGreetingStrategy;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Default implementation of {@link GreetingStrategyFactory}.
 * Maintains a registry of all known strategies. Lookup is O(1).
 *
 * <p>This class is itself instantiated exclusively by the
 * {@link AbstractGreetingStrategyFactoryFactory}, which ensures
 * proper initialisation order within the dependency graph.</p>
 */
public final class DefaultGreetingStrategyFactoryImpl implements GreetingStrategyFactory {

    private static final Logger LOG = Logger.getLogger(DefaultGreetingStrategyFactoryImpl.class.getName());

    private final Map<String, GreetingStrategy> registry = new HashMap<>();

    DefaultGreetingStrategyFactoryImpl() {
        registry.put(InformalGreetingStrategy.STRATEGY_NAME, new InformalGreetingStrategy());
        registry.put(FormalGreetingStrategy.STRATEGY_NAME, new FormalGreetingStrategy());
        LOG.fine("[StrategyFactory] Registered strategies: " + registry.keySet());
    }

    @Override
    public GreetingStrategy create(String strategyName) {
        GreetingStrategy strategy = registry.get(strategyName.toUpperCase());
        if (strategy == null) {
            throw new IllegalArgumentException(
                "No GreetingStrategy registered for name: '" + strategyName + "'. " +
                "Available strategies: " + registry.keySet() + ". " +
                "Register new strategies via AbstractGreetingStrategyFactoryFactory.");
        }
        return strategy;
    }
}
