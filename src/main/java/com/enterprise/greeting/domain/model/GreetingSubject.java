package com.enterprise.greeting.domain.model;

import java.util.Objects;
import java.util.Optional;

/**
 * Value Object representing the entity being greeted.
 * In the current domain model the subject is "world", but this Value Object
 * is already prepared for multi-subject, multi-tenant, multi-planetary deployments.
 */
public final class GreetingSubject implements Cloneable {

    private final String name;

    private GreetingSubject(String name) {
        this.name = Objects.requireNonNull(name, "Subject name must not be null. The universe must have a name.");
    }

    public static GreetingSubject of(String name) {
        return new GreetingSubject(name);
    }

    public static GreetingSubject world() {
        return new GreetingSubject("world");
    }

    public String getName() { return name; }

    public Optional<GreetingSubject> withSuffix(String suffix) {
        return Optional.of(new GreetingSubject(name + suffix));
    }

    @Override
    public GreetingSubject clone() {
        return new GreetingSubject(this.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GreetingSubject)) return false;
        return Objects.equals(name, ((GreetingSubject) o).name);
    }

    @Override public int hashCode()    { return Objects.hash(name); }
    @Override public String toString() { return "GreetingSubject{name='" + name + "'}"; }
}
