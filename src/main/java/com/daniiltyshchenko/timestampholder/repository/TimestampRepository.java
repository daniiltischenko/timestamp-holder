package com.daniiltyshchenko.timestampholder.repository;

import com.daniiltyshchenko.timestampholder.domain.persistence.TimestampsEntityDB;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

/**
 * {@link org.springframework.data.repository.Repository} for {@link TimestampsEntityDB}.
 *
 * @author Daniil Tyshchenko
 * @version 0.0.1
 * @since 0.0.1
 */
public interface TimestampRepository extends JpaRepository<TimestampsEntityDB, Integer> {

    TimestampsEntityDB findTimestampInfoByTimestamp(LocalDateTime timestamp);
}
