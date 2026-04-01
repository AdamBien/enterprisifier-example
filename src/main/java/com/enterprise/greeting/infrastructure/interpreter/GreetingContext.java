package com.enterprise.greeting.infrastructure.interpreter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Context object for the Greeting DSL interpreter.
 * Carries all variable bindings available during expression evaluation.
 * Acts as the environment in which expressions are interpreted.
 */
public final class GreetingContext {

    private final Map<String, String> variables = new HashMap<>();

    public void bind(String name, String value) {
        variables.put(name, value);
    }

    public Optional<String> resolve(String name) {
        return Optional.ofNullable(variables.get(name));
    }

    public static GreetingContext defaultContext() {
        GreetingContext ctx = new GreetingContext();
        ctx.bind("strategy",  "INFORMAL");
        ctx.bind("subject",   "world");
        ctx.bind("locale",    "en");
        ctx.bind("separator", ", ");
        return ctx;
    }
}
