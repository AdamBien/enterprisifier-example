package com.enterprise.greeting.infrastructure.adapter;

/**
 * Hexagonal outbound port for text output.
 * The domain and application layers must never call {@code System.out} directly.
 * All output is performed through this port, allowing the adapter to be
 * swapped for file output, network streams, or intergalactic communication channels
 * without touching a single line of domain code.
 */
public interface OutputPort {

    /**
     * Emits a line of text to the underlying output channel.
     *
     * @param line the text to emit; must not be {@code null}
     */
    void emitLine(String line);
}
