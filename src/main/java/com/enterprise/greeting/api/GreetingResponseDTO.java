package com.enterprise.greeting.api;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

/**
 * Immutable response Data Transfer Object leaving the application core.
 * This object carries the rendered greeting across the application-to-presentation boundary.
 * Under no circumstances may it reference a domain type.
 */
public final class GreetingResponseDTO {

    private final Optional<String> renderedGreeting;
    private final Optional<String> correlationId;
    private final Instant processedAt;
    private final Optional<String> strategyUsed;

    private GreetingResponseDTO(Builder builder) {
        this.renderedGreeting = Optional.ofNullable(builder.renderedGreeting);
        this.correlationId    = Optional.ofNullable(builder.correlationId);
        this.processedAt      = builder.processedAt != null ? builder.processedAt : Instant.now();
        this.strategyUsed     = Optional.ofNullable(builder.strategyUsed);
    }

    public Optional<String> getRenderedGreeting() { return renderedGreeting; }
    public Optional<String> getCorrelationId()    { return correlationId; }
    public Instant          getProcessedAt()       { return processedAt; }
    public Optional<String> getStrategyUsed()      { return strategyUsed; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GreetingResponseDTO)) return false;
        GreetingResponseDTO that = (GreetingResponseDTO) o;
        return Objects.equals(renderedGreeting, that.renderedGreeting)
            && Objects.equals(correlationId, that.correlationId)
            && Objects.equals(processedAt, that.processedAt)
            && Objects.equals(strategyUsed, that.strategyUsed);
    }

    @Override public int hashCode() {
        return Objects.hash(renderedGreeting, correlationId, processedAt, strategyUsed);
    }

    @Override public String toString() {
        return "GreetingResponseDTO{renderedGreeting=" + renderedGreeting
             + ", correlationId=" + correlationId
             + ", processedAt=" + processedAt
             + ", strategyUsed=" + strategyUsed + "}";
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String  renderedGreeting;
        private String  correlationId;
        private Instant processedAt;
        private String  strategyUsed;

        private Builder() {}

        public Builder renderedGreeting(String renderedGreeting) {
            this.renderedGreeting = renderedGreeting;
            return this;
        }

        public Builder correlationId(String correlationId) {
            this.correlationId = correlationId;
            return this;
        }

        public Builder processedAt(Instant processedAt) {
            this.processedAt = processedAt;
            return this;
        }

        public Builder strategyUsed(String strategyUsed) {
            this.strategyUsed = strategyUsed;
            return this;
        }

        public GreetingResponseDTO build() { return new GreetingResponseDTO(this); }
    }
}
