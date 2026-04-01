package com.enterprise.greeting.domain.model;

import java.util.Objects;
import java.util.Optional;

/**
 * Value Object representing the textual content of a greeting.
 * This object is immutable by design. Any transformation yields a new instance.
 * Primitive {@code String} must never appear in the domain model; use this wrapper.
 */
public final class GreetingMessage implements Cloneable {

    private final String value;

    private GreetingMessage(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                "GreetingMessage value must be non-null and non-blank. " +
                "The greeting pipeline detected an empty message, which is categorically unacceptable.");
        }
        this.value = value;
    }

    public static GreetingMessage of(String value) {
        return new GreetingMessage(value);
    }

    public String getValue() { return value; }

    public Optional<GreetingMessage> toUpperCase() {
        return Optional.of(new GreetingMessage(value.toUpperCase()));
    }

    public Optional<GreetingMessage> toLowerCase() {
        return Optional.of(new GreetingMessage(value.toLowerCase()));
    }

    @Override
    public GreetingMessage clone() {
        return new GreetingMessage(this.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GreetingMessage)) return false;
        return Objects.equals(value, ((GreetingMessage) o).value);
    }

    @Override public int hashCode()   { return Objects.hash(value); }
    @Override public String toString() { return "GreetingMessage{value='" + value + "'}"; }
}
