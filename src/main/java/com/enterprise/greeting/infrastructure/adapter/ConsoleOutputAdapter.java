package com.enterprise.greeting.infrastructure.adapter;

/**
 * Concrete hexagonal adapter implementing {@link OutputPort} for standard console output.
 * This is the only class in the entire system permitted to reference {@code System.out}.
 * All other classes must go through {@link OutputPort}.
 *
 * <p>Adapter implementations for file output, TCP sockets, message queues, smoke signals,
 * and Morse code are planned for future iterations.</p>
 */
public final class ConsoleOutputAdapter implements OutputPort {

    ConsoleOutputAdapter() {}

    @Override
    public void emitLine(String line) {
        System.out.println(line);
    }
}
