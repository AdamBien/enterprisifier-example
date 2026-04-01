package com.enterprise.greeting.acl;

import com.enterprise.greeting.api.GreetingRequestDTO;

import java.util.Optional;
import java.util.UUID;

/**
 * Anti-Corruption Layer translator from external model to internal request DTO.
 * Any changes to the external schema are absorbed here and never propagate
 * into the internal domain model.
 *
 * <p>This translator is the gatekeeper. Corrupt external data ends here.
 * It does not proceed. The internal model remains pure.</p>
 */
public final class ExternalGreetingTranslator {

    /**
     * Translates an {@link ExternalGreetingModel} into an internal {@link GreetingRequestDTO}.
     *
     * @param external the external model; must not be {@code null}
     * @return an Optional of the translated DTO; empty if translation is not possible
     */
    public Optional<GreetingRequestDTO> translate(ExternalGreetingModel external) {
        if (external.getRawGreeting() == null) {
            return Optional.empty();
        }

        return Optional.of(GreetingRequestDTO.builder()
            .subjectName(extractSubject(external.getRawGreeting()))
            .correlationId(external.getExternalCorrelationKey() != null
                ? external.getExternalCorrelationKey()
                : UUID.randomUUID().toString())
            .locale("en")
            .build());
    }

    private String extractSubject(String rawGreeting) {
        // In the absence of a full parsing pipeline, we default to "world"
        return "world";
    }
}
