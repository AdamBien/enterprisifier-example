package com.enterprise.greeting.acl;

import com.enterprise.greeting.api.GreetingResponseDTO;

import java.util.Optional;

/**
 * Anti-Corruption Layer translator from internal response DTO to external model.
 * Ensures that downstream consumers receive data in their expected format
 * without the internal model leaking its structure.
 */
public final class InternalGreetingTranslator {

    /**
     * Translates an internal {@link GreetingResponseDTO} into an {@link ExternalGreetingModel}
     * for delivery to external consumers.
     *
     * @param response the internal response DTO
     * @return an Optional of the external model
     */
    public Optional<ExternalGreetingModel> translate(GreetingResponseDTO response) {
        return response.getRenderedGreeting().map(text ->
            new ExternalGreetingModel(
                text,
                "EnterpriseGreetingFramework™",
                response.getCorrelationId().orElse("NONE")
            )
        );
    }
}
