package com.enterprise.greeting.application.command;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Command object encapsulating the intent to greet a subject.
 * The original {@code IO.println("hello, world")} call has been correctly replaced
 * by this command, which travels through the command bus, is validated by the chain
 * of responsibility, orchestrated by the saga, and ultimately executed by the handler.
 *
 * <p>Also supports undo via {@link UndoGreetCommand}, should the greeting prove unwelcome.</p>
 */
public final class GreetCommand implements Command {

    private final String commandId;
    private final Optional<String> subjectName;
    private final Optional<String> locale;
    private final Optional<String> strategyName;

    private GreetCommand(Builder builder) {
        this.commandId    = builder.commandId != null ? builder.commandId : UUID.randomUUID().toString();
        this.subjectName  = Optional.ofNullable(builder.subjectName);
        this.locale       = Optional.ofNullable(builder.locale);
        this.strategyName = Optional.ofNullable(builder.strategyName);
    }

    @Override
    public String getCommandId() { return commandId; }

    public Optional<String> getSubjectName()  { return subjectName; }
    public Optional<String> getLocale()       { return locale; }
    public Optional<String> getStrategyName() { return strategyName; }

    @Override public String toString() {
        return "GreetCommand{commandId='" + commandId
             + "', subjectName=" + subjectName
             + ", strategyName=" + strategyName + "}";
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String commandId;
        private String subjectName;
        private String locale;
        private String strategyName;

        private Builder() {}

        public Builder commandId(String commandId)       { this.commandId = commandId; return this; }
        public Builder subjectName(String subjectName)   { this.subjectName = subjectName; return this; }
        public Builder locale(String locale)             { this.locale = locale; return this; }
        public Builder strategyName(String strategyName) { this.strategyName = strategyName; return this; }

        public GreetCommand build() { return new GreetCommand(this); }
    }
}
