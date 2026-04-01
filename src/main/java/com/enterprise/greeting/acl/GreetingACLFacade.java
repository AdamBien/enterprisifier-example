package com.enterprise.greeting.acl;

import com.enterprise.greeting.api.GreetingRequestDTO;
import com.enterprise.greeting.api.GreetingResponseDTO;

import java.util.Optional;

/**
 * Facade for the Anti-Corruption Layer.
 * External systems interact with this facade only.
 * Internally it coordinates the inbound and outbound translators.
 *
 * <p>The ACL Facade is itself wrapped in a proxy in the presentation layer,
 * because no facade may be accessed directly either.</p>
 */
public final class GreetingACLFacade {

    private final ExternalGreetingTranslator inboundTranslator;
    private final InternalGreetingTranslator outboundTranslator;

    public GreetingACLFacade(ExternalGreetingTranslator inboundTranslator,
                              InternalGreetingTranslator outboundTranslator) {
        this.inboundTranslator  = inboundTranslator;
        this.outboundTranslator = outboundTranslator;
    }

    public Optional<GreetingRequestDTO> translateInbound(ExternalGreetingModel external) {
        return inboundTranslator.translate(external);
    }

    public Optional<ExternalGreetingModel> translateOutbound(GreetingResponseDTO response) {
        return outboundTranslator.translate(response);
    }
}
