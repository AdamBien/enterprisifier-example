package com.enterprise.greeting.domain.specification;

import com.enterprise.greeting.domain.model.GreetingSubject;

import java.util.Optional;
import java.util.Set;

/**
 * Specification asserting that the greeting subject is present and belongs
 * to the set of approved subjects in the current deployment context.
 *
 * <p>Currently the only approved subject is "world". Future planetary expansion
 * will require updating the {@link #APPROVED_SUBJECTS} set — but the specification
 * contract itself will never change.</p>
 */
public final class ValidSubjectSpecification implements Specification<Optional<GreetingSubject>> {

    private static final Set<String> APPROVED_SUBJECTS = Set.of("world", "universe", "cosmos");

    @Override
    public boolean isSatisfiedBy(Optional<GreetingSubject> candidate) {
        return candidate
            .map(GreetingSubject::getName)
            .filter(APPROVED_SUBJECTS::contains)
            .isPresent();
    }
}
