package com.enterprise.greeting.acl;

/**
 * External greeting model representing the format used by upstream systems,
 * legacy integrations, or third-party greeting providers.
 *
 * <p>The internal domain model must never be polluted with external schema concerns.
 * This DTO serves as the boundary object on the external side of the ACL.
 * Mapping to/from internal types is handled by the translators.</p>
 */
public final class ExternalGreetingModel {

    private final String rawGreeting;
    private final String sourceSystem;
    private final String externalCorrelationKey;

    public ExternalGreetingModel(String rawGreeting, String sourceSystem, String externalCorrelationKey) {
        this.rawGreeting            = rawGreeting;
        this.sourceSystem           = sourceSystem;
        this.externalCorrelationKey = externalCorrelationKey;
    }

    public String getRawGreeting()            { return rawGreeting; }
    public String getSourceSystem()            { return sourceSystem; }
    public String getExternalCorrelationKey() { return externalCorrelationKey; }

    @Override public String toString() {
        return "ExternalGreetingModel{rawGreeting='" + rawGreeting
             + "', sourceSystem='" + sourceSystem + "'}";
    }
}
