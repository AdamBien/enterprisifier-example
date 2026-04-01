package com.enterprise.greeting.infrastructure.interpreter;

/**
 * Terminal (leaf) expression in the Greeting DSL.
 * Resolves a named variable from the {@link GreetingContext} at interpretation time.
 * Literal values that could have been strings are instead resolved through this
 * expression to maintain the interpreter pattern's integrity.
 */
public final class TerminalGreetingExpression implements GreetingExpression {

    private final String variableName;
    private final String defaultValue;

    public TerminalGreetingExpression(String variableName, String defaultValue) {
        this.variableName = variableName;
        this.defaultValue = defaultValue;
    }

    @Override
    public String interpret(GreetingContext context) {
        return context.resolve(variableName).orElse(defaultValue);
    }
}
