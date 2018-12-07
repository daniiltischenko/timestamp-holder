package com.daniiltyshchenko.timestampholder.domain.persistence;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Entity class for representing database {@code timestamp_info} table.
 * Each timestamp entry should be unique and not nullable.
 *
 * @author Daniil Tyshchenko
 * @version 0.0.1
 * @since 0.0.1
 */
@Entity
@Table(name = "timestamps")
public class TimestampsEntityDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(nullable = false, unique = true)
    private LocalDateTime timestamp;

    public Integer getId() {
        return id;
    }

    public TimestampsEntityDB setId(Integer id) {
        this.id = id;
        return this;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public TimestampsEntityDB setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TimestampsEntityDB that = (TimestampsEntityDB) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, timestamp);
    }

    @Override
    public String toString() {
        return "TimestampsEntityDB{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                '}';
    }
}
