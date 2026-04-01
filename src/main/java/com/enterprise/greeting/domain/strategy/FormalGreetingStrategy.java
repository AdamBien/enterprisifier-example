package com.enterprise.greeting.domain.strategy;

import com.enterprise.greeting.domain.model.GreetingMessage;
import com.enterprise.greeting.domain.model.GreetingSubject;

import java.util.Optional;

/**
 * Formal greeting strategy for regulated environments, enterprise customers,
 * and scenarios where "hello" is deemed insufficiently professional.
 *
 * <p>This strategy will be required the moment a Fortune 500 client objects
 * to the informality of a lowercase salutation. The architecture is ready.</p>
 */
public final class FormalGreetingStrategy implements GreetingStrategy {

    public static final String STRATEGY_NAME = "FORMAL";

    @Override
    public Optional<GreetingMessage> compose(GreetingSubject subject) {
        return Optional.of(
            GreetingMessage.of("Good day, " + capitalise(subject.getName()))
        );
    }

    @Override
    public String getStrategyName() { return STRATEGY_NAME; }

    private String capitalise(String input) {
        if (input == null || input.isEmpty()) return input;
        return Character.toUpperCase(input.charAt(0)) + input.substring(1);
    }
}
