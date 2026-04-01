package com.enterprise.greeting.application.handler;

import com.enterprise.greeting.application.command.GreetCommand;
import com.enterprise.greeting.domain.model.GreetingSubject;
import com.enterprise.greeting.domain.specification.ValidSubjectSpecification;

import java.util.Optional;
import java.util.logging.Logger;

/**
 * Chain handler responsible for validating the subject of a {@link GreetCommand}.
 * Uses the {@link ValidSubjectSpecification} to evaluate correctness.
 * Aborts the chain if validation fails by returning {@code Optional.empty()}.
 */
public final class GreetingValidationHandler extends AbstractGreetingHandler {

    private static final Logger LOG = Logger.getLogger(GreetingValidationHandler.class.getName());

    private final ValidSubjectSpecification specification = new ValidSubjectSpecification();

    @Override
    protected Optional<GreetCommand> process(GreetCommand command) {
        Optional<GreetingSubject> subject = command.getSubjectName()
            .map(GreetingSubject::of);

        if (!specification.isSatisfiedBy(subject)) {
            LOG.warning("[Validation] Subject rejected: " + command.getSubjectName());
            return Optional.empty();
        }

        LOG.fine("[Validation] Subject validated: " + command.getSubjectName());
        return Optional.of(command);
    }
}
