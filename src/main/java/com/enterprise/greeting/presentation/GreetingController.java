package com.enterprise.greeting.presentation;

import com.enterprise.greeting.api.GreetingResponseDTO;
import com.enterprise.greeting.config.GreetingRegistry;

import java.util.Optional;
import java.util.logging.Logger;

/**
 * Presentation-layer controller. The topmost class in the call stack.
 * Resolves the {@link GreetingFacade} from the {@link GreetingRegistry}
 * and invokes the default greeting use-case.
 *
 * <p>This class may be driven by a CLI entry point, a REST resource,
 * a message consumer, or a scheduled job — none of these delivery mechanisms
 * affect the classes below this layer.</p>
 */
public final class GreetingController {

    private static final Logger LOG = Logger.getLogger(GreetingController.class.getName());

    public void execute() {
        LOG.info("[Controller] Resolving GreetingFacade from registry...");

        GreetingFacade facade = GreetingRegistry.getInstance().require(GreetingFacade.class);

        Optional<GreetingResponseDTO> response = facade.greetDefault();

        response.ifPresentOrElse(
            dto -> LOG.info("[Controller] Greeting completed. correlationId="
                + dto.getCorrelationId().orElse("NONE")),
            () -> LOG.warning("[Controller] Greeting pipeline returned empty response.")
        );
    }
}
