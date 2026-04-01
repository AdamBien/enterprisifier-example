package com.enterprise.greeting.infrastructure.factory;

import com.enterprise.greeting.domain.strategy.GreetingStrategy;

/**
 * Factory interface for {@link GreetingStrategy} instances.
 * The direct instantiation of any strategy via {@code new} is forbidden.
 * All strategy creation must pass through this factory to ensure the
 * strategy registry, flyweight pool, and decoration pipeline are applied.
 */
public interface GreetingStrategyFactory {

    /**
     * Creates or retrieves a strategy for the given strategy name.
     *
     * @param strategyName the canonical strategy name (e.g. "INFORMAL", "FORMAL")
     * @return the strategy instance; never {@code null}
     * @throws IllegalArgumentException if no strategy is registered for the given name
     */
    GreetingStrategy create(String strategyName);
}
