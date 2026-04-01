package com.enterprise.greeting.domain.specification;

import com.enterprise.greeting.domain.model.GreetingMessage;

import java.util.Optional;

/**
 * Specification asserting that a {@link GreetingMessage} is non-null, non-empty, and non-blank.
 * This rule is the cornerstone of greeting integrity. An empty greeting is not a greeting;
 * it is an existential void that must be rejected at the domain boundary.
 */
public final class NonEmptyMessageSpecification implements Specification<Optional<GreetingMessage>> {

    @Override
    public boolean isSatisfiedBy(Optional<GreetingMessage> candidate) {
        return candidate
            .map(GreetingMessage::getValue)
            .filter(v -> !v.isBlank())
            .isPresent();
    }
}
