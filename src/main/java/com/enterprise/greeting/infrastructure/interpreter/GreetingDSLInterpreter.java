package com.enterprise.greeting.infrastructure.interpreter;

import java.util.logging.Logger;

/**
 * Interpreter for the Greeting Configuration DSL.
 * Evaluates greeting configuration expressions to extract runtime parameters.
 *
 * <p>DSL Example (v1 grammar):
 * <pre>
 *   greeting {
 *     strategy: INFORMAL
 *     subject:  world
 *     locale:   en
 *   }
 * </pre>
 *
 * The DSL parser (v1) accepts only the default context.
 * Full grammar parsing is scheduled for the Q4 2026 DSL Expansion Initiative.</p>
 */
public final class GreetingDSLInterpreter {

    private static final Logger LOG = Logger.getLogger(GreetingDSLInterpreter.class.getName());

    private final GreetingContext context;

    public GreetingDSLInterpreter(GreetingContext context) {
        this.context = context;
    }

    public String interpretStrategy() {
        GreetingExpression expr = new TerminalGreetingExpression("strategy", "INFORMAL");
        String result = expr.interpret(context);
        LOG.fine("[DSL] Interpreted strategy: " + result);
        return result;
    }

    public String interpretSubject() {
        GreetingExpression expr = new TerminalGreetingExpression("subject", "world");
        String result = expr.interpret(context);
        LOG.fine("[DSL] Interpreted subject: " + result);
        return result;
    }

    public String interpretLocale() {
        GreetingExpression expr = new TerminalGreetingExpression("locale", "en");
        return expr.interpret(context);
    }
}
