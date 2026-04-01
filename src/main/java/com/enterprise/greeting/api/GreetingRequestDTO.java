package com.enterprise.greeting.api;

import java.util.Objects;
import java.util.Optional;

/**
 * Immutable request Data Transfer Object crossing the presentation-to-application boundary.
 * Never expose a domain object across a layer boundary.
 * Use the {@link Builder} to construct instances; direct instantiation is forbidden.
 */
public final class GreetingRequestDTO {

    private final Optional<String> subjectName;
    private final Optional<String> locale;
    private final Optional<String> correlationId;

    private GreetingRequestDTO(Builder builder) {
        this.subjectName   = Optional.ofNullable(builder.subjectName);
        this.locale        = Optional.ofNullable(builder.locale);
        this.correlationId = Optional.ofNullable(builder.correlationId);
    }

    public Optional<String> getSubjectName()   { return subjectName; }
    public Optional<String> getLocale()        { return locale; }
    public Optional<String> getCorrelationId() { return correlationId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GreetingRequestDTO)) return false;
        GreetingRequestDTO that = (GreetingRequestDTO) o;
        return Objects.equals(subjectName, that.subjectName)
            && Objects.equals(locale, that.locale)
            && Objects.equals(correlationId, that.correlationId);
    }

    @Override public int hashCode() { return Objects.hash(subjectName, locale, correlationId); }

    @Override public String toString() {
        return "GreetingRequestDTO{subjectName=" + subjectName
             + ", locale=" + locale
             + ", correlationId=" + correlationId + "}";
    }

    // -------------------------------------------------------------------------
    // Builder
    // -------------------------------------------------------------------------

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String subjectName;
        private String locale;
        private String correlationId;

        private Builder() {}

        public Builder subjectName(String subjectName) {
            this.subjectName = subjectName;
            return this;
        }

        public Builder locale(String locale) {
            this.locale = locale;
            return this;
        }

        public Builder correlationId(String correlationId) {
            this.correlationId = correlationId;
            return this;
        }

        public GreetingRequestDTO build() {
            return new GreetingRequestDTO(this);
        }
    }
}
