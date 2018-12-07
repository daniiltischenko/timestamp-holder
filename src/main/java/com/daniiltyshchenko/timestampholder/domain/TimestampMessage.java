package com.daniiltyshchenko.timestampholder.domain;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Wrapper class for timestamp event messages.
 *
 * @author Daniil Tyshchenko
 * @version 0.0.1
 * @since 0.0.1
 */
public class TimestampMessage {

    private LocalDateTime timestamp;

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public TimestampMessage setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimestampMessage that = (TimestampMessage) o;
        return Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {

        return Objects.hash(timestamp);
    }

    @Override
    public String toString() {
        return "TimestampMessage{" +
                "timestamp=" + timestamp +
                '}';
    }
}
