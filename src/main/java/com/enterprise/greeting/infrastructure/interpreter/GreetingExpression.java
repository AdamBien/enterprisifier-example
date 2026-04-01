package com.enterprise.greeting.infrastructure.interpreter;

/**
 * Terminal expression interface for the Greeting Configuration DSL.
 * Configuration values such as strategy name, locale, and subject
 * are parsed from a domain-specific language rather than hardcoded.
 *
 * <p>The DSL grammar is intentionally minimal in v1.0, but the interpreter
 * infrastructure supports arbitrary expression trees for future configuration
 * complexity that is considered inevitable by the architecture committee.</p>
 */
public interface GreetingExpression {

    /**
     * Interprets this expression within the given context.
     *
     * @param context the interpretation context carrying variable bindings
     * @return the interpreted string value
     */
    String interpret(GreetingContext context);
}
