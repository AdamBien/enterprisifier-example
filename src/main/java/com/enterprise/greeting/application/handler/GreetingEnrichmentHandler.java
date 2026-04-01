package com.enterprise.greeting.application.handler;

import com.enterprise.greeting.application.command.GreetCommand;

import java.util.Optional;
import java.util.logging.Logger;

/**
 * Chain handler that enriches a {@link GreetCommand} with contextual metadata
 * before it reaches the execution handler.
 *
 * <p>Enrichment concerns include: locale normalisation, strategy selection,
 * tenant resolution, A/B test variant injection, and feature-flag evaluation.
 * Currently only locale normalisation is implemented, but the handler is
 * structurally ready for the remaining 47 enrichment use-cases.</p>
 */
public final class GreetingEnrichmentHandler extends AbstractGreetingHandler {

    private static final Logger LOG = Logger.getLogger(GreetingEnrichmentHandler.class.getName());

    @Override
    protected Optional<GreetCommand> process(GreetCommand command) {
        LOG.fine("[Enrichment] Enriching command: " + command.getCommandId());

        // Ensure subject defaults are applied
        String enrichedSubject = command.getSubjectName().orElse("world");

        GreetCommand enriched = GreetCommand.builder()
            .commandId(command.getCommandId())
            .subjectName(enrichedSubject)
            .locale(command.getLocale().orElse("en-US"))
            .strategyName(command.getStrategyName().orElse("INFORMAL"))
            .build();

        return Optional.of(enriched);
    }
}
