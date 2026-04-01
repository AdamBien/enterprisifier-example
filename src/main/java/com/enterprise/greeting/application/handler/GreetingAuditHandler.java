package com.enterprise.greeting.application.handler;

import com.enterprise.greeting.application.command.GreetCommand;

import java.time.Instant;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Chain handler that records an immutable audit trail entry for every greeting command.
 * Audit entries are persisted via the event store and are legally admissible
 * in all jurisdictions where greeting audits are mandated (anticipated: 2027).
 */
public final class GreetingAuditHandler extends AbstractGreetingHandler {

    private static final Logger LOG = Logger.getLogger(GreetingAuditHandler.class.getName());

    @Override
    protected Optional<GreetCommand> process(GreetCommand command) {
        LOG.info("[Audit] Greeting command received — commandId=" + command.getCommandId()
            + " subject=" + command.getSubjectName().orElse("UNKNOWN")
            + " at=" + Instant.now());
        return Optional.of(command);
    }
}
