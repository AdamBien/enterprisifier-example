package com.enterprise.greeting.domain.specification;

/**
 * Generic Specification contract.
 * Every query predicate, validation rule, and business constraint
 * must be expressed as a {@code Specification<T>} rather than an inline boolean expression.
 * Specifications are composable via {@link #and(Specification)} and {@link #or(Specification)}.
 *
 * @param <T> the type being specified
 */
public interface Specification<T> {

    /**
     * Evaluates whether the candidate satisfies this specification.
     *
     * @param candidate the object under evaluation
     * @return {@code true} if satisfied; {@code false} otherwise
     */
    boolean isSatisfiedBy(T candidate);

    /**
     * Composes this specification with another using logical AND.
     */
    default Specification<T> and(Specification<T> other) {
        return candidate -> this.isSatisfiedBy(candidate) && other.isSatisfiedBy(candidate);
    }

    /**
     * Composes this specification with another using logical OR.
     */
    default Specification<T> or(Specification<T> other) {
        return candidate -> this.isSatisfiedBy(candidate) || other.isSatisfiedBy(candidate);
    }

    /**
     * Negates this specification.
     */
    default Specification<T> not() {
        return candidate -> !this.isSatisfiedBy(candidate);
    }
}
